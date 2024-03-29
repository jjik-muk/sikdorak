import { Button } from '@mui/material';
import { MESSAGE } from 'constants/message';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import useUploadImage from 'hooks/useUploadImage';
import { useSelector } from 'react-redux';
import { RootState } from 'store/modules/store';
import { fetchData } from 'utils/fetch';
import { openWarningToast } from 'utils/toast';

function RegistrationBtn({ selectedImg, dispatchReviews, toggleIsReviewWrite }: any) {
  const [reviewWriteState, dispatchReviewWriteState] = useReviewWrite();
  const { content, year, month, date, rating, scope, tags, images, store, presignedUrl } = reviewWriteState;
  const { placeId, storeName, x, y } = store;
  const { uploadImageToS3 } = useUploadImage();
  const accountStore = useSelector((state: RootState) => state.account);

  return (
    <Button variant="contained" onClick={handleRegistration} sx={{ height: '40px' }}>
      게시
    </Button>
  );

  async function handleRegistration() {
    const hasImage = !!presignedUrl;
    if (!accountStore.id) {
      openWarningToast(MESSAGE.ERROR.NEED_LOGIN);
      return;
    }
    if (hasImage) {
      await uploadImageToS3(presignedUrl, selectedImg);
    }
    registerPost();
  }

  async function registerPost() {
    if (!store?.placeId) {
      openWarningToast('식당을 선택해주세요.');
      return;
    }
    if (year === 0 && month === 0 && date === 0) {
      openWarningToast('방문일을 선택해주세요.');
      return;
    }
    if (!rating) {
      openWarningToast('평점을 입력해주세요.');
      return;
    }
    const storeValidationRes = await fetchData({
      path: `api/stores`,
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
    await fetchData({ path: `api/reviews`, method: 'POST', bodyData, withAccessToken: true });
    const newReview = {
      reviewContent: content,
      reviewScore: rating,
      reviewVisibility: scope,
      tags,
      images,
      store,
    };
    dispatchReviews({ type: 'ADD_REVIEW', review: newReview });
    dispatchReviewWriteState({ type: 'RESET_STATE' });
    toggleIsReviewWrite();
  }
}

export default RegistrationBtn;
