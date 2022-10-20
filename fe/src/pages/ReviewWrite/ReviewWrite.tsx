import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RegistrationBtn from 'components/ReviewWrite/RegistrationBtn/RegistrationBtn';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import { TEXT } from 'constants/text';
import useUploadImage from 'hooks/useUploadImage';
import { accountStore } from 'stores/AccountStore';
import { Content, Header, Img, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';
import CloseIcon from '@mui/icons-material/Close';

function ReviewWrite({ toggleIsReviewWrite, dispatchReviews }: ReviewWriteProps) {
  const { selectedImg, setSelectedImg } = useUploadImage();
  const { nickname, profileImage } = accountStore;

  return (
    <Wrap>
      <Title>리뷰 작성하기</Title>
      <Content>
        <ImgWrap>
          {selectedImg ? (
            <>
              <Img width="90%" height="90%" src={URL.createObjectURL(selectedImg)} alt={TEXT.ALT.PHOTOGRAPH} />
              <CloseIcon onClick={handleRemoveSelectedImg} sx={{ position: 'absolute', top: '20px', right: '15px', cursor: 'pointer' }} />
            </>
          ) : (
            <ImgUpload setSelectedImg={setSelectedImg} />
          )}
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

  function handleRemoveSelectedImg() {
    setSelectedImg(null);
  }
}

export default ReviewWrite;

type ReviewWriteProps = {
  toggleIsReviewWrite?: () => void;
  dispatchReviews?: any;
};
