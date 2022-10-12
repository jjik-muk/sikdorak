import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RegistrationBtn from 'components/ReviewWrite/RegistrationBtn/RegistrationBtn';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import useUploadImage from 'hooks/useUploadImage';
import { accountStore } from 'stores/AccountStore';
import { Content, Header, Img, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite({ toggleIsReviewWrite, dispatchReviews }: ReviewWriteProps) {
  const { selectedImg, setSelectedImg } = useUploadImage();
  const { nickname, profileImage } = accountStore;

  return (
    <Wrap>
      <Title>리뷰 작성하기</Title>
      <Content>
        <ImgWrap>
          {selectedImg ? <Img width="100%" height="100%" src={URL.createObjectURL(selectedImg)} alt="업로드 사진" /> : <ImgUpload setSelectedImg={setSelectedImg} />}
        </ImgWrap>
        <InputWrap>
          <Header>
            <Profile imgUrl={profileImage} nickname={nickname} />
            <RegistrationBtn selectedImg={selectedImg} dispatchReviews={dispatchReviews} toggleIsReviewWrite={toggleIsReviewWrite} />
          </Header>
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

type ReviewWriteProps = {
  toggleIsReviewWrite?: () => void;
  dispatchReviews?: any;
};
