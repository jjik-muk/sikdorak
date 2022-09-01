import { DETAIL, FEED } from 'constants/size';
import Icon from 'common/Icon';
import Portal from 'common/Portal/Portal';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
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

function Feed({ author, contents, pictures, store, likeCnt }: FeedProps) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const reviewDetailModalRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);
  const { name, region } = store;

  return (
    <>
      <Wrap onClick={toggleIsClikedFeed}>
        <ContentsWrap wrapWidth={DETAIL.WRAP.WIDTH_NO_IMG}>
          <Header>
            <UserProfile nickname={author} />
            <MenuWrap onClick={toggleIsActiveMenu}>
              <Icon icon="MenuBtn" />
              {isActiveMenu && <Menu />}
            </MenuWrap>
          </Header>
          <Main>
            <Contents>{contents}</Contents>
            <Pictures>
              {pictures &&
                pictures.map((picture, i) => (
                  <img
                    key={createKey(picture, i)}
                    src={picture}
                    alt="음식"
                    width={FEED.IMG.WIDTH}
                    height={FEED.IMG.HEIGHT}
                  />
                ))}
            </Pictures>
            <MainFooter>
              <CompnayProfile company={name} region={region} />
            </MainFooter>
          </Main>
          <ButtonWrapper>
            <div onClick={toggleIsActiveHeart}>
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
          <ReviewDetail author={author} contents={contents} pictures={pictures} store={store} likeCnt={likeCnt} />
        </Portal>
      )}
    </>
  );

  function handleCopyURL() {
    const curURL = window.location.href;
    const { clipboard } = navigator;
    clipboard.writeText(curURL);
  }
}

export default Feed;

export type FeedProps = {
  author: string;
  contents: string;
  pictures?: string[];
  store: { name: string; region: string };
  likeCnt: number;
};
