import Icon from 'common/Icon';
import Button from 'components/Button/Button';
import Carousel from 'components/Carousel/Carousel';
import Comment from 'components/Comment/Comment';
import CompnayProfile from 'components/CompanyProfile/CompanyProfile';
import Profile from 'components/Profile/Profile';
import TagList from 'components/Tag/TagList/TagList';
import TotalRating from 'components/TotalRating/TotalRating';
import WriteComment from 'components/WriteComment/WriteComment';
import { Contents } from 'pages/ReviewDetail/ReviewDetail.styled';
import { ButtonWrapper, ContentsWrap, Header, Main, MainFooter, Wrap } from './ReviewDetailWithPicture.styled';

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

function ReviewDetailWithPicture() {
  return (
    <Wrap>
      <Carousel urls={IMAGE_URLS} />
      <ContentsWrap>
        <Header>
          <Profile nickname="Dashawn" />
          <Icon icon="MenuBtn" />
        </Header>
        <Main>
          <Contents>..흠... 할머니가 북어를 복어로 하셔서 그런지 목숨의 위험이 느껴지는 맛이었습니다.</Contents>
          <TotalRating taste={5} price={3} service={1} />
          <MainFooter>
            <CompnayProfile company="호이 초밥" region="부산" />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <Button icon="Heart" width={93} height={28} />
          <Button icon="TalkBubble" width={93} height={28} />
          <Button icon="ShareArrow" width={93} height={28} />
        </ButtonWrapper>
        <TagList tags={['#초밥', '#맛집', '#부산']} />
        {COMMENTS.map(({ title, content }) => (
          <Comment title={title} content={content} />
        ))}
        <WriteComment />
      </ContentsWrap>
    </Wrap>
  );
}

export default ReviewDetailWithPicture;
