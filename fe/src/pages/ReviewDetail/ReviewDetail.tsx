import { COMMENTS } from 'constants/dummyData';
import { DETAIL, FEED } from 'constants/size';
import Icon from 'common/Icon';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import { FeedProps } from 'components/ReviewList/Feed/Feed';
import TagList from 'components/ReviewWrite/Tag/TagList/TagList';
import useToggle from 'hooks/useToggle';
import { useId, useRef } from 'react';
import { createKey } from 'utils/utils';
import { ButtonWrapper, Contents, ContentsWrap, Header, IconWrap, Main, MainFooter, Wrap } from './ReviewDetail.styled';

function ReviewDetail({ author, contents, pictures, store, likeCnt }: FeedProps) {
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const hasPicture = pictures.length > 0;
  const wrapWidth = hasPicture ? DETAIL.WRAP.WIDTH_WITH_IMG : DETAIL.WRAP.WIDTH_NO_IMG;
  const btnWidth = hasPicture ? FEED.BTN.WIDTH_WITH_IMG : FEED.BTN.WIDTH_NO_IMG;
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const id = useId();
  const { storeName, storeAddress } = store;

  const clickCreateComment = () => {
    commentRef.current.focus();
  };

  return (
    <Wrap>
      {hasPicture && <Carousel urls={pictures} />}
      <ContentsWrap wrapWidth={wrapWidth}>
        <Header>
          <Profile nickname={author} />
          <div onClick={toggleIsActiveMenu}>
            <Icon icon="MenuBtn" />
            {isActiveMenu && <Menu />}
          </div>
        </Header>
        <Main>
          <Contents>{contents}</Contents>
          <MainFooter>
            <CompnayProfile company={storeName} region={storeAddress} />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <div onClick={toggleIsActiveHeart}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
              {likeCnt}
            </IconWrap>
          </div>
          <div onClick={clickCreateComment}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={handleCopyURL}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="ShareArrow" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
        </ButtonWrapper>
        <TagList tags={['#초밥', '#맛집', '#부산']} />
        {COMMENTS.map(({ title, content }, idx) => (
          <Comment key={createKey(id, idx)} title={title} content={content} />
        ))}
        <WriteComment commentRef={commentRef} />
      </ContentsWrap>
    </Wrap>
  );

  function handleCopyURL() {
    const curURL = window.location.href;
    const { clipboard } = navigator;
    clipboard.writeText(curURL);
  }
}

export default ReviewDetail;
