import { DOMAIN } from 'constants/dummyData';
import { DETAIL, FEED } from 'constants/size';
import Icon from 'components/Common/Icon/Icon';
import Portal from 'components/Common/Portal/Portal';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef } from 'react';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import {
  ButtonWrapper,
  Contents,
  ContentsWrap,
  Header,
  IconWrap,
  Main,
  MainFooter,
  MenuWrap,
  Pictures,
  Wrap,
} from './Feed.styled';

function Feed({ images, reviewContent, reviewId, reviewScore, store, user, likeCnt }: FeedProps) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const reviewDetailModalRef = useRef(null);
  const menuRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  return (
    <>
      <Wrap onClick={toggleIsClikedFeed}>
        <ContentsWrap wrapWidth={DETAIL.WRAP.WIDTH_NO_IMG}>
          <Header>
            <UserProfile nickname={user?.userNickname} />
            <MenuWrap onClick={handleMenu}>
              <Icon icon="MenuBtn" />
              {isActiveMenu && <Menu menuRef={menuRef} />}
            </MenuWrap>
          </Header>
          <Main>
            <Contents>{reviewContent}</Contents>
            <Pictures>
              {images &&
                images.map((image, i) => (
                  <img
                    key={createKey(image, i)}
                    src={image}
                    alt="음식"
                    width={FEED.IMG.WIDTH}
                    height={FEED.IMG.HEIGHT}
                  />
                ))}
            </Pictures>
            <Rating rating={reviewScore} />
            <MainFooter>
              <CompnayProfile company={store?.storeName} region={store?.storeAddress} />
            </MainFooter>
          </Main>
          <ButtonWrapper>
            <div onClick={handleLike}>
              <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
                <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
                {likeCnt}
              </IconWrap>
            </div>
            <div>
              <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
                <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
              </IconWrap>
            </div>
            <div onClick={handleCopyURL}>
              <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
                <Icon icon="ShareArrow" fill={isActiveHeart ? 'red' : '#FFF'} />
              </IconWrap>
            </div>
          </ButtonWrapper>
        </ContentsWrap>
      </Wrap>
      {isClikedFeed && (
        <Portal selector="#portal" ref={reviewDetailModalRef}>
          <ReviewDetail
            images={images}
            reviewContent={reviewContent}
            reviewId={reviewId}
            reviewScore={reviewScore}
            store={store}
            user={user}
            likeCnt={0}
          />
        </Portal>
      )}
    </>
  );

  function handleMenu(e) {
    toggleIsActiveMenu();
    e.stopPropagation();
  }

  function handleLike(e) {
    const path = isActiveHeart ? 'unlike' : 'like';
    const URL = `${DOMAIN}/api/reviews/${reviewId}/${path}`;
    const options = { method: 'PUT' };

    fetchDataThatNeedToLogin(URL, options);
    toggleIsActiveHeart();
    e.stopPropagation();
  }

  function handleCopyURL(e) {
    const curURL = window.location.href;
    const { clipboard } = navigator;

    clipboard.writeText(curURL);
    e.stopPropagation();
  }
}

export default Feed;

export type FeedProps = {
  images: string[];
  reviewContent: string;
  reviewId: number;
  reviewScore: number;
  store: { storeId: number; storeName: string; storeAddress: string };
  user: { userId: number; userNickname: string; userProfileImage: string };
  likeCnt: number;
};
