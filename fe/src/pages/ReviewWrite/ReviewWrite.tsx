import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import { Content, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite() {
  return (
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
        </InputWrap>
      </Content>
    </Wrap>
  );
}

export default ReviewWrite;