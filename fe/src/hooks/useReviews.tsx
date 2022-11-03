import { useReducer, useState } from 'react';
import { fetchData } from 'utils/fetch';

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
  const fetchNextReviews = async (url) => {
    const res = await fetchData({ path: url, withAccessToken: true });
    const nextReviews = res.data.reviews;
    const nextAfterParam = res.data.page.next;

    dispatchReviews({ type: 'ADD_REVIEWS', reviews: nextReviews });
    setAfterParam(nextAfterParam);

    const isLastPage = res.data.page.last;
    if (isLastPage) {
      setHasNextPage(false);
    }
  };
  async function initReviews(url) {
    setAfterParam(0);
    const res = await fetchData({ path: url, withAccessToken: true });
    const nextReviews = res.data.reviews;
    const nextAfterParam = res.data.page.next;

    dispatchReviews({ type: 'ADD_REVIEWS', reviews: nextReviews });
    setAfterParam(nextAfterParam);

    const isLastPage = res.data.page.last;
    if (isLastPage) {
      setHasNextPage(false);
    }
  }
  return { reviews, dispatchReviews, handleScroll, fetchNextReviews, afterParam, setAfterParam, initReviews };
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
    case 'RESET_REVIEWS':
      return [];
    default:
      return state;
  }
}

type ActionType = {
  type: string;
  reviews?: object[];
  reviewId?: number;
};
