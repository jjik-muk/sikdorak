import Icon, { IconComponentsKeys } from 'common/Icon';
import Logo from 'common/Logo/Logo';
import Portal from 'common/Portal/Portal';
import { useUserInfo } from 'context/userInfoProvider';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import { createKey } from 'utils/utils';
import { ButtonWrap, IconWrap, Input, SearchFormWrap, Header, Wrap } from './CommonHeader.styled';

function CommonHeader() {
  const [isReviewWrite, setIsReviewWrite] = useState(false);
  const [, toggleIsUserProfile] = useToggle(false);
  const reviewWriteModalRef = useRef(null);
  const userDetailModalRef = useRef(null);
  const [userInfo] = useUserInfo();
  const { profileImageUrl } = userInfo;
  console.log('profileImageUrl', profileImageUrl);

  const iconInfo: IconInfoProps[] = [
    { icon: 'Home', handler: toggleIsUserProfile, to: '/' },
    { icon: 'Map', to: '/map' },
    { icon: 'PostBtn', handler: handleReviewWrite, to: '' },
    { icon: 'Alarm', to: '' },
    { icon: 'Profile', to: '/userDetail' },
  ];

  useOutsideClick(reviewWriteModalRef, handleReviewWrite);
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
          {/* TODO: 로그인하면 프로필 사진으로 Icon 대체 */}
          {iconInfo.map(({ icon, handler, to }, idx) => (
            <Link key={createKey(icon, idx)} to={to}>
              <div key={createKey(icon, idx)} onClick={handler}>
                <Icon icon={icon} width={24} height={24} />
              </div>
            </Link>
          ))}
        </ButtonWrap>
        {isReviewWrite && (
          <Portal selector="#portal" ref={reviewWriteModalRef}>
            <ReviewWrite />
          </Portal>
        )}
      </Header>
    </Wrap>
  );

  function handleReviewWrite() {
    setIsReviewWrite(!isReviewWrite);
  }
}

type IconInfoProps = {
  icon: IconComponentsKeys;
  handler?: React.MouseEventHandler<HTMLDivElement>;
  to?: string;
};

export default CommonHeader;
