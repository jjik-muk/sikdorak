import { API_PATH } from 'constants/apiPath';
import { STATUS_CODE } from 'constants/statusCode';
import { useState } from 'react';
import { fetchData } from 'utils/fetch';
import { openErrorToast } from 'utils/toast';
import useToggle from './useToggle';

function useLike({ like, reviewId }) {
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(like.userLikeStatus);
  const [likeCnt, setLikeCnt] = useState(like.count);

  async function postLike() {
    const { message, code } = await fetchData({ path: API_PATH.REVIEW.LIKE(reviewId), method: 'PUT', withAccessToken: true });
    if (code !== STATUS_CODE.SUCCESS.LIKE) {
      openErrorToast(`${code} : ${message}`);
      return;
    }
    setLikeCnt(likeCnt + 1);
    toggleIsActiveHeart();
  }

  async function postUnlike() {
    const { message, code } = await fetchData({ path: API_PATH.REVIEW.UNLIKE(reviewId), method: 'PUT', withAccessToken: true });
    if (code !== STATUS_CODE.SUCCESS.CANCEL_LIKE) {
      openErrorToast(`${code} : ${message}`);
      return;
    }
    setLikeCnt(likeCnt - 1);
    toggleIsActiveHeart();
  }

  return { isActiveHeart, likeCnt, postLike, postUnlike };
}

export default useLike;
