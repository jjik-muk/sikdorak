import Icon from 'common/Icon';
import Button from 'components/Button/Button';
import Comment from 'components/Comment/Comment';
import CompnayProfile from 'components/CompanyProfile/CompanyProfile';
import Profile from 'components/Profile/Profile';
import Textarea from 'components/Textarea/Textarea';
import { ButtonWrapper, Header, Main, MainFooter, Wrap } from './ReviewDetail.styled';

export default function ReviewDetail() {
  return (
    <Wrap>
      <Header>
        <Profile nickname="Dashawn" />
        <Icon icon="MenuBtn" />
      </Header>
      <Main>
        <Textarea />
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
    </Wrap>
  );
}
