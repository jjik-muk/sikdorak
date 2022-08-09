import Icon from 'common/Icon';
import Button from 'components/Button/Button';
import Carousel from 'components/Carousel/Carousel';
import Comment from 'components/Comment/Comment';
import CompnayProfile from 'components/CompanyProfile/CompanyProfile';
import Profile from 'components/Profile/Profile';
import Textarea from 'components/Textarea/Textarea';
import { ButtonWrapper, ContentsWrap, Header, Main, MainFooter, Wrap } from './ReviewDetailWithPicture.styled';

function ReviewDetailWithPicture() {
  return (
    <Wrap>
      <Carousel />
      <ContentsWrap>
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
