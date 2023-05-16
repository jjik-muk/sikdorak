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
import { Content, Header, Img, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';
import CloseIcon from '@mui/icons-material/Close';
import { Button } from '@mui/material';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { requestReviewModification } from 'request/request';
import { reloadBrowser } from 'utils/utils';
import { useSelector } from 'react-redux';
import { RootState } from 'store/modules/store';

function ReviewWrite({ toggleIsReviewWrite, dispatchReviews, isModify, reviewId }: ReviewWriteProps) {
  const [reviewWrite, dispatchReviewWrite] = useReviewWrite();
  const { selectedImg, setSelectedImg, selectedImgUrl, setSelectedImgUrl, uploadImageToS3 } = useUploadImage();
  const accountStore = useSelector((state: RootState) => state.account);
  const { nickname, profileImage } = accountStore;

  return (
    <Wrap>
      <Title>리뷰 작성하기</Title>
      <Content>
        <ImgWrap>
          {reviewWrite.images[0] ? (
            <>
              <Img width="90%" height="90%" src={selectedImgUrl} alt={TEXT.ALT.PHOTOGRAPH} />
              <CloseIcon onClick={handleRemoveSelectedImg} sx={{ position: 'absolute', top: '20px', right: '15px', cursor: 'pointer' }} />
            </>
          ) : (
            <ImgUpload setSelectedImg={setSelectedImg} setSelectedImgUrl={setSelectedImgUrl} />
          )}
        </ImgWrap>
        <InputWrap>
          <Header>
            <Profile imgUrl={profileImage} nickname={nickname} />
            {isModify ? (
              <Button onClick={handleReviewModification} variant="contained" sx={{ height: '40px' }}>
                수정
              </Button>
            ) : (
              <RegistrationBtn selectedImg={selectedImg} dispatchReviews={dispatchReviews} toggleIsReviewWrite={toggleIsReviewWrite} />
            )}
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

  async function handleReviewModification() {
    const month = String(reviewWrite.month).length === 1 ? `0${reviewWrite.month}` : reviewWrite.month;
    const date = String(reviewWrite.date).length === 1 ? `0${reviewWrite.date}` : reviewWrite.date;

    const hasImageThatNeedToUpload = reviewWrite.presignedUrl;
    if (hasImageThatNeedToUpload) {
      await uploadImageToS3(reviewWrite.presignedUrl, selectedImg);
    }

    await requestReviewModification({
      reviewId,
      body: {
        reviewContent: reviewWrite.content,
        storeId: reviewWrite.store.storeId,
        reviewScore: reviewWrite.rating,
        reviewVisibility: reviewWrite.scope,
        visitedDate: `${reviewWrite.year}-${month}-${date}`,
        tags: reviewWrite.tags,
        images: reviewWrite.images,
      },
    });
    dispatchReviewWrite({ type: 'RESET_STATE' });
    toggleIsReviewWrite();
    reloadBrowser();
  }

  function handleRemoveSelectedImg() {
    setSelectedImg(null);
    dispatchReviewWrite({ type: 'SET_IMAGES', images: [] });
  }
}

export default ReviewWrite;

type ReviewWriteProps = {
  toggleIsReviewWrite?: () => void;
  dispatchReviews?: any;
  isModify?: boolean;
  reviewId?: number;
};
