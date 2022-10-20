import { ICON } from 'styles/size';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import { observer } from 'mobx-react';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import { useRef, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import { createKey } from 'utils/utils';
import Icon, { IconComponentsKeys } from '../Icon/Icon';
import Logo from '../Logo/Logo';
import Portal from '../Portal/Portal';
import { ButtonWrap, Header, ProfileImageWrap, Wrap } from './CommonHeader.styled';

const CommonHeader = observer(({ dispatchReviews }: any) => {
  const [isReviewWrite, toggleIsReviewWrite] = useToggle(false);
  const [, toggleIsUserProfile] = useToggle(false);
  const reviewWriteModalRef = useRef(null);
  const userDetailModalRef = useRef(null);

  const iconInfo: IconInfoProps[] = accountStore.id
    ? [
        { icon: 'Home', handler: toggleIsUserProfile, to: '/' },
        { icon: 'Map', to: '/map' },
        { icon: 'PostBtn', handler: toggleIsReviewWrite, to: '' },
      ]
    : [
        { icon: 'Home', handler: toggleIsUserProfile, to: '/' },
        { icon: 'Map', to: '/map' },
      ];

  useOutsideClick(reviewWriteModalRef, toggleIsReviewWrite);
  useOutsideClick(userDetailModalRef, toggleIsUserProfile);

  useEffect(() => {
    accountStore.setMyInfo();
  }, []);

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
          <Link to={accountStore.id ? `/user/${accountStore.id}` : '/login'}>
            <ProfileImageWrap>
              {accountStore.profileImage ? (
                <img src={accountStore.profileImage} alt="profile" width={ICON.MEDIUM} height={ICON.MEDIUM} />
              ) : (
                <AccountCircleIcon sx={{ width: ICON.MEDIUM, height: ICON.MEDIUM }} />
              )}
            </ProfileImageWrap>
          </Link>
        </ButtonWrap>
        {isReviewWrite && (
          <Portal selector="#portal" ref={reviewWriteModalRef}>
            <ReviewWrite toggleIsReviewWrite={toggleIsReviewWrite} dispatchReviews={dispatchReviews} />
          </Portal>
        )}
      </Header>
    </Wrap>
  );
});

type IconInfoProps = {
  icon: IconComponentsKeys;
  handler?: React.MouseEventHandler<HTMLDivElement>;
  to?: string;
};

export default CommonHeader;