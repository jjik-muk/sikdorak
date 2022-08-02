import Icon from 'common/Icon';
import Button from 'components/Button/Button';
import CompnayProfile from 'components/CompanyProfile/CompanyProfile';
import Profile from 'components/Profile/Profile';
import Textarea from 'components/Textarea/Textarea';
import { ButtonWrapper, Header, Main, MainFooter } from './ReviewDetail.styled';

export default function ReviewDetail() {
  return (
    <>
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
        <Button icon="Heart" text="좋아요" />
        <Button icon="TalkBubble" text="댓글 달기" />
        <Button icon="ShareArrow" text="공유하기" />
      </ButtonWrapper>
      {/* 댓글 영역 */}
    </>
  );
}
