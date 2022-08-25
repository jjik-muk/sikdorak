import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RegistrationBtn from 'components/ReviewWrite/RegistrationBtn/RegistrationBtn';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import ReviewWriteProvider from 'context/reviewWriteProvider';
import { Content, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite() {
  return (
    <ReviewWriteProvider>
      <Wrap>
        <Title>리뷰 작성하기</Title>
        <Content>
          <ImgWrap>
            <ImgUpload />
          </ImgWrap>
          <InputWrap>
            <Profile nickname="호이" />
            <Textarea />
            <RestaurantSearch />
            <SelectDay />
            <WriteRating />
            <TagContainer />
            <PostScope />
            <RegistrationBtn />
          </InputWrap>
        </Content>
      </Wrap>
    </ReviewWriteProvider>
  );
}

export default ReviewWrite;
