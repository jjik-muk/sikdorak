import { WRITE } from 'constants/size';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RegistrationBtn from 'components/ReviewWrite/RegistrationBtn/RegistrationBtn';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import useUploadImage from 'hooks/useUploadImage';
import { Content, Img, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite({ toggleIsReviewWrite, dispatchReviews }: ReviewWriteProps) {
  const { selectedImg, setSelectedImg } = useUploadImage();
  const [{ nickname, profileImageUrl }] = useMyUserInfo();

  return (
    <Wrap>
      <Title>리뷰 작성하기</Title>
      <Content>
        <ImgWrap>
          {selectedImg ? (
            <Img
              width={WRITE.IMG.WIDTH}
              height={WRITE.IMG.HEIGHT}
              src={URL.createObjectURL(selectedImg)}
              alt="업로드 사진"
            />
          ) : (
            <ImgUpload setSelectedImg={setSelectedImg} />
          )}
        </ImgWrap>
        <InputWrap>
          <Profile imgUrl={profileImageUrl} nickname={nickname} />
          <Textarea />
          <RestaurantSearch />
          <SelectDay />
          <WriteRating />
          <TagContainer />
          <PostScope />
          <div>
            <RegistrationBtn
              selectedImg={selectedImg}
              dispatchReviews={dispatchReviews}
              toggleIsReviewWrite={toggleIsReviewWrite}
            />
          </div>
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
