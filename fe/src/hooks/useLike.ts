import { API_PATH } from 'constants/apiPath';
import { STATUS_CODE } from 'constants/statusCode';
import { useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import useToggle from './useToggle';

function useLike({ like, reviewId }) {
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(like.userLikeStatus);
  const [likeCnt, setLikeCnt] = useState(like.count);

  async function postLike() {
    const { message, code } = await fetchDataThatNeedToLogin(API_PATH.REVIEW.LIKE(reviewId), { method: 'PUT' });
    if (code !== STATUS_CODE.SUCCESS.LIKE) {
      alert(`${code} : ${message}`);
      return;
    }
    setLikeCnt(likeCnt + 1);
    toggleIsActiveHeart();
  }

  async function postUnlike() {
    const { message, code } = await fetchDataThatNeedToLogin(API_PATH.REVIEW.UNLIKE(reviewId), { method: 'PUT' });
    if (code !== STATUS_CODE.SUCCESS.CANCEL_LIKE) {
      alert(`${code} : ${message}`);
      return;
    }
    setLikeCnt(likeCnt - 1);
    toggleIsActiveHeart();
  }

  return { isActiveHeart, likeCnt, postLike, postUnlike };
}

export default useLike;
