import { useCallback, useReducer, useState } from 'react';
import { accountStore } from 'stores/AccountStore';
import { fetchData, fetchDataThatNeedToLogin } from 'utils/utils';

function useReviews() {
  const [reviews, dispatchReviews] = useReducer(reducer, []);
  const [afterParam, setAfterParam] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);

  function handleScroll(e, url) {
    const { scrollHeight, scrollTop, clientHeight } = e.target as HTMLDivElement;
    const isScrollEnd = scrollHeight - scrollTop === clientHeight;

    if (hasNextPage && isScrollEnd) {
      fetchNextReviews(url);
    }
  }
  const fetchNextReviews = useCallback(async (url) => {
    const res = accountStore.id ? await fetchDataThatNeedToLogin(url) : await fetchData(`${process.env.REACT_APP_BE_SERVER_URL}/${url}`);
    const nextReviews = res.data.reviews;
    const nextAfterParam = res.data.page.next;

    dispatchReviews({ type: 'ADD_REVIEWS', reviews: nextReviews });
    setAfterParam(nextAfterParam);

    const isLastPage = res.data.page.last;
    if (isLastPage) {
      setHasNextPage(false);
    }
  }, []);

  return { reviews, dispatchReviews, handleScroll, fetchNextReviews, afterParam };
}

export default useReviews;

function reducer(state, action: ActionType) {
  switch (action.type) {
    case 'SET_REVIEWS':
      return action.reviews;
    case 'ADD_REVIEWS':
      return [...state, ...action.reviews];
    case 'DELETE_REVIEW':
      return state.filter((review) => review.reviewId !== action.reviewId);
    default:
      return state;
  }
}

type ActionType = {
  type: string;
  reviews?: object[];
  reviewId?: number;
};
