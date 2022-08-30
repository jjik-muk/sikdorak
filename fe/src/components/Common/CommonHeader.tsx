import Icon, { IconComponentsKeys } from 'common/Icon';
import Logo from 'common/Logo/Logo';
import Portal from 'common/Portal/Portal';
import { useReviewDetail } from 'context/reviewDetailProvider';
import { useUserInfo } from 'context/userInfoProvider';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import { useRef } from 'react';
import { Link } from 'react-router-dom';
import { createKey } from 'utils/utils';
import { ButtonWrap, IconWrap, Input, SearchFormWrap, Header, Wrap, ProfileImageWrap } from './CommonHeader.styled';

function CommonHeader() {
  const [isReviewWrite, toggleIsReviewWrite] = useToggle(false);
  const [, toggleIsUserProfile] = useToggle(false);
  const reviewWriteModalRef = useRef(null);
  const userDetailModalRef = useRef(null);
  const [, dispatchReviewDetail] = useReviewDetail();
  const [userInfo] = useUserInfo();
  const { profileImageUrl } = userInfo;

  const iconInfo: IconInfoProps[] = [
    { icon: 'Home', handler: toggleIsUserProfile, to: '/' },
    { icon: 'Map', to: '/map' },
    { icon: 'PostBtn', handler: toggleIsReviewWrite, to: '' },
  ];

  useOutsideClick(reviewWriteModalRef, toggleIsReviewWrite);
  useOutsideClick(userDetailModalRef, toggleIsUserProfile);

  return (
    <Wrap>
      <Header>
        <Logo />
        <SearchFormWrap>
          <Input type="text" placeholder="검색해주세요." />
          <IconWrap>
            <Icon icon="SearchBtn" width={18} height={18} />
          </IconWrap>
        </SearchFormWrap>
        <ButtonWrap>
          {iconInfo.map(({ icon, handler, to }, idx) => (
            <Link key={createKey(icon, idx)} to={to}>
              <div onClick={handler}>
                <Icon icon={icon} width={24} height={24} />
              </div>
            </Link>
          ))}
          <Link to="/userDetail">
            <ProfileImageWrap
              onClick={() => {
                const myUserId = JSON.parse(localStorage.getItem('MY_INFO')).userId;
                dispatchReviewDetail({ type: 'SET_USER', userId: myUserId });
              }}
            >
              <img src={profileImageUrl} alt="profile" width={24} height={24} />
            </ProfileImageWrap>
          </Link>
        </ButtonWrap>
        {isReviewWrite && (
          <Portal selector="#portal" ref={reviewWriteModalRef}>
            <ReviewWrite />
          </Portal>
        )}
      </Header>
    </Wrap>
  );
}

type IconInfoProps = {
  icon: IconComponentsKeys;
  handler?: React.MouseEventHandler<HTMLDivElement>;
  to?: string;
};

export default CommonHeader;
