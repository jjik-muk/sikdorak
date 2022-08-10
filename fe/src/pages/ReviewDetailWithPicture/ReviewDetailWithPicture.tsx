import Icon from 'common/Icon';
import Button from 'components/Button/Button';
import Carousel from 'components/Carousel/Carousel';
import Comment from 'components/Comment/Comment';
import CompnayProfile from 'components/CompanyProfile/CompanyProfile';
import Profile from 'components/Profile/Profile';
import { Contents } from 'pages/ReviewDetail/ReviewDetail.styled';
import { ButtonWrapper, ContentsWrap, Header, Main, MainFooter, Wrap } from './ReviewDetailWithPicture.styled';

const IMAGE_URLS = [
  'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?20201002142956&q=80&rw=750&rh=536',
  'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000881/img/ko/a0000881_parts_586c7364bbcdc.jpg?20200630185341&q=80&rw=686&rh=490',
  'https://blog.kakaocdn.net/dn/u8bU3/btq9nhUNEgR/kBXjA4SUp2WFK3AiXRzN4k/img.png',
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
          <MainFooter>
            <CompnayProfile company="할머니 북어탕" region="일산" />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <Button icon="Heart" width={93} height={28} />
          <Button icon="TalkBubble" width={93} height={28} />
          <Button icon="ShareArrow" width={93} height={28} />
        </ButtonWrapper>
        <Comment title="호이" />
        <Comment title="럼카" content="스타일 왜 이따위인가요?" />
        <Comment title="쿠킴" content="프론트 맘에 안드네요" />
        <Comment title="포키" content="다시" />
        <Comment title="제이" content="해오세요" />
      </ContentsWrap>
    </Wrap>
  );
}

export default ReviewDetailWithPicture;
