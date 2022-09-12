import { DETAIL, FEED } from 'constants/size';
import Icon from 'components/Common/Icon/Icon';
import Portal from 'components/Common/Portal/Portal';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import useLike from 'hooks/useLike';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef } from 'react';
import { createKey } from 'utils/utils';
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

function Feed({
  images,
  like = { count: 0, userLikeStatus: false },
  reviewContent,
  reviewId,
  reviewScore,
  store,
  user,
  isUsedMapPage,
}: FeedProps) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const { isActiveHeart, likeCnt, postLike } = useLike({ like, reviewId });

  const reviewDetailModalRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);
  const menuRef = useRef(null);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  const [myUserInfo] = useMyUserInfo();
  const myUserId = myUserInfo.userId;
  const isMyFeed = user?.userId === myUserId;
  const BTN_WIDTH = isUsedMapPage ? 125 : FEED.BTN.WIDTH_NO_IMG;

  return (
    <>
      <Wrap onClick={toggleIsClikedFeed}>
        <ContentsWrap wrapWidth={isUsedMapPage ? 500 : DETAIL.WRAP.WIDTH_NO_IMG}>
          <Header>
            <UserProfile nickname={user?.userNickname} />
            <MenuWrap onClick={handleMenu}>
              {isMyFeed && <Icon icon="MenuBtn" />}
              {isActiveMenu && <Menu menuRef={menuRef} reviewId={reviewId} />}
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
              <IconWrap width={BTN_WIDTH} height={FEED.BTN.HEIGHT}>
                <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
                {likeCnt}
              </IconWrap>
            </div>
            <div>
              <IconWrap width={BTN_WIDTH} height={FEED.BTN.HEIGHT}>
                <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
              </IconWrap>
            </div>
            <div onClick={handleCopyURL}>
              <IconWrap width={BTN_WIDTH} height={FEED.BTN.HEIGHT}>
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
            like={like}
            reviewContent={reviewContent}
            reviewId={reviewId}
            reviewScore={reviewScore}
            store={store}
            user={user}
            isActiveHeart={isActiveHeart}
            likeCnt={likeCnt}
            postLike={postLike}
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
    postLike();
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
  like: { count: number; userLikeStatus: boolean };
  reviewContent: string;
  reviewId: number;
  reviewScore: number;
  store: { storeId: number; storeName: string; storeAddress: string };
  user: { userId: number; userNickname: string; userProfileImage: string };
  isUsedMapPage?: boolean;
};
