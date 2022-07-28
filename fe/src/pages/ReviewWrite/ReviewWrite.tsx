import ImgUpload from 'components/ImgUpload/ImgUpload';
import PostScope from 'components/PostScope/PostScope';
import Profile from 'components/Profile/Profile';
import RestaurantSearch from 'components/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/SelectDay/SelectDay';
import TagContainer from 'components/Tag/TagContainer/TagContainer';
import Textarea from 'components/Textarea/Textarea';
import WriteRating from 'components/WriteRating/WriteRating';
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
