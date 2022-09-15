import { useReviewWrite } from 'context/ReviewWriteProvider';
import useUploadImage from 'hooks/useUploadImage';
import { fetchData, fetchDataThatNeedToLogin } from 'utils/utils';
import { Wrap } from './RegistrationBtn.styled';

function RegistrationBtn({ selectedImg, dispatchReviews, toggleIsReviewWrite }: any) {
  const [reviewWriteState] = useReviewWrite();
  const { content, year, month, date, rating, scope, tags, images, store, presignedUrl } = reviewWriteState;
  const { placeId, storeName, x, y } = store;
  const { uploadImageToS3 } = useUploadImage();

  return (
    <Wrap onClick={handleRegistration}>
      <div>게시</div>
    </Wrap>
  );

  async function handleRegistration() {
    const hasImageThatNeedToUpload = presignedUrl;
    if (hasImageThatNeedToUpload) {
      await uploadImageToS3(presignedUrl, selectedImg);
    }
    registerPost();
  }

  async function registerPost() {
    // TODO: 미입력 경고 추가
    if (!store) {
      alert('식당을 선택해주세요.');
      return;
    }
    if (year === 0 && month === 0 && date === 0) {
      alert('방문일을 선택해주세요.');
      return;
    }
    const storeValidationRes = await fetchData(`${process.env.REACT_APP_BE_SERVER_URL}/api/stores`, {
      method: 'PUT',
      bodyData: {
        placeId,
        storeName,
        x,
        y,
      },
    });
    const bodyData = {
      reviewContent: content,
      storeId: storeValidationRes.data.storeId,
      reviewScore: rating,
      reviewVisibility: scope,
      visitedDate: `${year}-${month.padStart(2, '0')}-${date.padStart(2, '0')}`,
      tags,
      images,
    };
    fetchDataThatNeedToLogin(`api/reviews`, { method: 'POST', bodyData });
    const newReview = {
      reviewContent: content,
      reviewScore: rating,
      reviewVisibility: scope,
      tags,
      images,
      store,
    };
    dispatchReviews({ type: 'ADD_REVIEW', review: newReview });
    toggleIsReviewWrite();
  }
}

export default RegistrationBtn;
