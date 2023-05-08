import { ICON } from 'styles/size';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import { useRef, useEffect } from 'react';
import { MenuItem } from '@mui/material';
import { Link } from 'react-router-dom';
import { createKey, reloadBrowser } from 'utils/utils';
import Icon, { IconComponentsKeys } from '../Icon/Icon';
import Logo from '../Logo/Logo';
import Portal from '../Portal/Portal';
import { ButtonWrap, Header, ProfileImageWrap, Wrap } from './CommonHeader.styled';
import styled from 'styled-components';
import { COLOR } from 'styles/color';
import { useDispatch, useSelector } from 'react-redux';
import { AccountAction, fetchMyInfo } from 'store/modules/account';
import { RootState } from 'store/modules/store';
import { ThunkDispatch } from 'redux-thunk';

function CommonHeader({ dispatchReviews }: any) {
  const [isReviewWrite, toggleIsReviewWrite] = useToggle(false);
  const [, toggleIsUserProfile] = useToggle(false);
  const reviewWriteModalRef = useRef(null);
  const userDetailModalRef = useRef(null);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const menuRef = useRef(null);
  const dispatch: ThunkDispatch<RootState, null, AccountAction> = useDispatch();
  const accountStore = useSelector((state: RootState) => state.account);

  const iconInfo: IconInfoProps[] = [
    { icon: 'Home', handler: toggleIsUserProfile, to: '/' },
    { icon: 'Map', to: '/map' },
    { icon: 'PostBtn', handler: toggleIsReviewWrite, to: '' },
  ];

  useOutsideClick(reviewWriteModalRef, toggleIsReviewWrite);
  useOutsideClick(userDetailModalRef, toggleIsUserProfile);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  useEffect(() => {
    dispatch(fetchMyInfo());
  }, [accountStore.accessToken]);

  return (
    <Wrap>
      <Header>
        <Logo />
        <ButtonWrap>
          {iconInfo.map(({ icon, handler, to }, idx) => (
            <Link key={createKey(icon, idx)} to={to}>
              <div onClick={handler}>
                <Icon icon={icon} width={ICON.MEDIUM} height={ICON.MEDIUM} />
              </div>
            </Link>
          ))}
          <ProfileImageWrap>
            {accountStore.profileImage ? (
              <div onClick={toggleIsActiveMenu}>
                <Avatar src={accountStore.profileImage} alt="profile" sx={{ width: ICON.MEDIUM, height: ICON.MEDIUM }} />
                {isActiveMenu && (
                  <MenuWrap ref={menuRef}>
                    <MenuItem>
                      <Link to={`/user/${accountStore.id}`}>내 프로필</Link>
                    </MenuItem>
                    <MenuItem onClick={handleLogout}>
                      <Link to="/">로그아웃</Link>
                    </MenuItem>
                  </MenuWrap>
                )}
              </div>
            ) : (
              <Link to="/login">
                <Button variant="contained">로그인</Button>
              </Link>
            )}
          </ProfileImageWrap>
        </ButtonWrap>
        {isReviewWrite && (
          <Portal selector="#portal" ref={reviewWriteModalRef}>
            <ReviewWrite toggleIsReviewWrite={toggleIsReviewWrite} dispatchReviews={dispatchReviews} />
          </Portal>
        )}
      </Header>
    </Wrap>
  );
}

function handleLogout() {
  localStorage.removeItem('accessToken');
  reloadBrowser();
}

type IconInfoProps = {
  icon: IconComponentsKeys;
  handler?: React.MouseEventHandler<HTMLDivElement>;
  to?: string;
};

export default CommonHeader;

const MenuWrap = styled.div`
  position: absolute;
  width: 100px;
  height: fit-content;
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  background-color: ${COLOR.WHITE};
  border-radius: 5px;
`;
