import Icon from 'common/Icon';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import TotalRating from 'components/ReviewDetail/TotalRating/TotalRating';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import TagList from 'components/ReviewWrite/Tag/TagList/TagList';
import { useId, useRef, useState } from 'react';
import { createKey } from 'utils/utils';
import { ButtonWrapper, Contents, ContentsWrap, Header, IconWrap, Main, MainFooter, Wrap } from './ReviewDetail.styled';

const IMAGE_URLS = [
  'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?20201002142956&q=80&rw=750&rh=536',
  'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000881/img/ko/a0000881_parts_586c7364bbcdc.jpg?20200630185341&q=80&rw=686&rh=490',
  'https://blog.kakaocdn.net/dn/u8bU3/btq9nhUNEgR/kBXjA4SUp2WFK3AiXRzN4k/img.png',
];

const COMMENTS = [
  { title: '럼카', content: '동해물과 백두산이' },
  { title: '호이', content: '마르고 닳도록' },
  { title: '쿠킴', content: '하느님이 보우하사' },
  { title: '포키', content: '우리나라 만세' },
  { title: '제이', content: '무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.' },
];

function ReviewDetailWithPicture({ hasPicture }: { hasPicture?: boolean }) {
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const wrapWidth = hasPicture ? 400 : 750;
  const btnWidth = hasPicture ? 90 : 190;
  const [isActiveHeart, setIsActiveHeart] = useState(false);
  const [isActiveMenu, setIsActiveMenu] = useState(false);
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
          <div onClick={handleMenu}>
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
          <div onClick={handleToggleHeart}>
            <IconWrap width={btnWidth} height={30}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={clickCreateComment}>
            <IconWrap width={btnWidth} height={30}>
              <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={handleCopyURL}>
            <IconWrap width={btnWidth} height={30}>
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

  function handleMenu() {
    if (isActiveMenu) {
      setIsActiveMenu(false);
      return;
    }
    setIsActiveMenu(true);
  }

  function handleToggleHeart() {
    if (isActiveHeart) {
      setIsActiveHeart(false);
      return;
    }
    setIsActiveHeart(true);
  }

  function handleCopyURL() {
    const curURL = window.location.href;
    const { clipboard } = navigator;
    clipboard.writeText(curURL);
  }
}

export default ReviewDetailWithPicture;
