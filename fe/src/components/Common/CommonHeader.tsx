import Icon, { IconComponentsKeys } from 'common/Icon';
import Logo from 'common/Logo/Logo';
import Portal from 'common/Portal/Portal';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import UserDetail from 'pages/UserDetail/userDetail';
import { useRef, useState } from 'react';
import { createKey } from 'utils/utils';
import { ButtonWrap, IconWrap, Input, SearchFormWrap, Header, Wrap } from './CommonHeader.styled';

function CommonHeader() {
  const [isReviewWrite, setIsReviewWrite] = useState(false);
  const [isUserProfile, toggleIsUserProfile] = useToggle(false);
  const reviewWriteModalRef = useRef(null);
  const userDetailModalRef = useRef(null);

  const iconInfo: IconInfoProps[] = [
    { icon: 'Home', handler: toggleIsUserProfile },
    { icon: 'Map' },
    { icon: 'PostBtn', handler: handleReviewWrite },
    { icon: 'Alarm' },
    { icon: 'Profile' },
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
          {iconInfo.map(({ icon, handler }, idx) => (
            <div onClick={handler}>
              <Icon key={createKey(icon, idx)} icon={icon} width={24} height={24} />
            </div>
          ))}
        </ButtonWrap>
        {isReviewWrite && (
          <Portal selector="#portal" ref={reviewWriteModalRef}>
            <ReviewWrite />
          </Portal>
        )}
        {isUserProfile && (
          <Portal selector="#portal" ref={userDetailModalRef}>
            <UserDetail />
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
};

export default CommonHeader;
