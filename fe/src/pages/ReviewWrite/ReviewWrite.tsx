import ImgUpload from 'components/ImgUpload/ImgUpload';
import PostScope from 'components/PostScope/PostScope';
import RestaurantSearch from 'components/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/SelectDay/SelectDay';
import { Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite() {
  return (
    <>
      <Title>리뷰 작성하기</Title>
      <Wrap>
        <ImgUpload />
        <RestaurantSearch />
        <PostScope />
        <SelectDay />
      </Wrap>
    </>
  );
}

export default ReviewWrite;
