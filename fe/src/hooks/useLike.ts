import { DOMAIN } from 'constants/dummyData';
import { useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import useToggle from './useToggle';

function useLike({ like, reviewId }) {
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(like.userLikeStatus);
  const [likeCnt, setLikeCnt] = useState(like.count);

  function postLike() {
    const path = isActiveHeart ? 'unlike' : 'like';
    const URL = `${DOMAIN}/api/reviews/${reviewId}/${path}`;
    const options = { method: 'PUT' };

    if (isActiveHeart) {
      setLikeCnt(likeCnt - 1);
    } else {
      setLikeCnt(likeCnt + 1);
    }

    fetchDataThatNeedToLogin(URL, options);
    toggleIsActiveHeart();
  }

  return { isActiveHeart, likeCnt, postLike };
}

export default useLike;
