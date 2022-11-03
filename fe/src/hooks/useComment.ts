import { useState } from 'react';
import { fetchData } from 'utils/fetch';

function useComment({ reviewId }) {
  const [comments, setComments] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
  const COMMENT_SIZE = 5;
  const [hasNextComments, setHasNextComments] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);

  async function fetchAndSetComments({ saveMethod }) {
    const after = saveMethod === 'OVERWRITE' ? 0 : afterParam;
    const res = await requestComments({ reviewId, afterParam: after });

    setHasNextComments(!res.data.page.last);
    if (!res.data.page.last) {
      setAfterParam(res.data.page.next);
    }

    switch (saveMethod) {
      case 'OVERWRITE':
        setComments(res.data.comments);
        break;
      case 'ACCUMULATE':
        setComments([...comments, ...res.data.comments]);
        break;
      default:
        throw new Error('saveMethod의 값이 잘못되었습니다.');
    }
  }

  async function requestComments({ reviewId, afterParam }) {
    const res = await fetchData({ path: `api/reviews/${reviewId}/comments?size=${COMMENT_SIZE}&after=${afterParam}` });
    return res;
  }

  async function requestAddComment({ commentValue }) {
    const res = await fetchData({
      path: `api/reviews/${reviewId}/comments`,
      method: 'POST',
      bodyData: {
        content: commentValue,
      },
      withAccessToken: true,
    });
    setIsSubmitted(true);
    return res;
  }

  return { fetchAndSetComments, hasNextComments, comments, setComments, requestAddComment, isSubmitted, setIsSubmitted };
}

export default useComment;
