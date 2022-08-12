import Icon from 'common/Icon';
import Button from 'common/IconButton/IconButton';
import Comment from 'components/ReviewDetail/Comment/Comment';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import TotalRating from 'components/ReviewDetail/TotalRating/TotalRating';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import { ButtonWrapper, Contents, Header, Main, MainFooter, Wrap } from './ReviewDetail.styled';

export default function ReviewDetail() {
  return (
    <Wrap>
      <Header>
        <Profile nickname="Dashawn" />
        <Icon icon="MenuBtn" />
      </Header>
      <Main>
        <Contents>..흠... 할머니가 북어를 복어로 하셔서 그런지 목숨의 위험이 느껴지는 맛이었습니다.</Contents>
        <TotalRating taste={5} price={3} service={1} />
        <MainFooter>
          <CompnayProfile company="할머니 북어탕" region="일산" />
        </MainFooter>
      </Main>
      <ButtonWrapper>
        <Button icon="Heart" width={190} height={24}>
          좋아요
        </Button>
        <Button icon="TalkBubble" width={190} height={24}>
          댓글달기
        </Button>
        <Button icon="ShareArrow" width={190} height={24}>
          공유하기
        </Button>
      </ButtonWrapper>
      <Comment title="호이" />
      <Comment title="럼카" content="스타일 왜 이따위인가요?" />
      <Comment title="쿠킴" content="프론트 맘에 안드네요" />
      <Comment title="포키" content="다시" />
      <Comment title="제이" content="해오세요" />
      <WriteComment />
    </Wrap>
  );
}
