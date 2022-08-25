import { COMMENTS, IMAGE_URLS } from 'constants/dummyData';
import { DETAIL, FEED } from 'constants/size';
import Icon from 'common/Icon';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import TotalRating from 'components/ReviewDetail/TotalRating/TotalRating';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import TagList from 'components/ReviewWrite/Tag/TagList/TagList';
import useToggle from 'hooks/useToggle';
import { useId, useRef } from 'react';
import { createKey } from 'utils/utils';
import { ButtonWrapper, Contents, ContentsWrap, Header, IconWrap, Main, MainFooter, Wrap } from './ReviewDetail.styled';

function ReviewDetailWithPicture({ hasPicture }: { hasPicture?: boolean }) {
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const wrapWidth = hasPicture ? DETAIL.WRAP.WIDTH_WITH_IMG : DETAIL.WRAP.WIDTH_NO_IMG;
  const btnWidth = hasPicture ? FEED.BTN.WIDTH_WITH_IMG : FEED.BTN.WIDTH_NO_IMG;
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const id = useId();

  const clickCreateComment = () => {
    commentRef.current.focus();
  };

  return (
    <Wrap>
      {hasPicture && <Carousel urls={IMAGE_URLS} />}
      <ContentsWrap wrapWidth={wrapWidth}>
        <Header>
          <Profile nickname="Dashawn" />
          <div onClick={toggleIsActiveMenu}>
            <Icon icon="MenuBtn" />
            {isActiveMenu && <Menu />}
          </div>
        </Header>
        <Main>
          <Contents>..흠... 할머니가 북어를 복어로 하셔서 그런지 목숨의 위험이 느껴지는 맛이었습니다.</Contents>
          <TotalRating taste={5} price={3} service={1} />
          <MainFooter>
            <CompnayProfile company="호이 초밥" region="부산" />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <div onClick={toggleIsActiveHeart}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
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

export default ReviewDetailWithPicture;
