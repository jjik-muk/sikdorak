import { STATUS_CODE } from 'constants/statusCode';
import { useState, useEffect } from 'react';
import { fetchData } from 'utils/fetch';

export const MAP_POS_DEFAULT = { lat: 37.509389, lng: 127.105143 };
const PAGING_SIZE = 6;
const RADIUS_METER = 2000;

export function useReviewsBasedLocation() {
  const [reviews, setReviews] = useState([]);
  const [storesOfReviews, setStoresOfReviews] = useState([]);
  const [mapPos, setMapPos] = useState(MAP_POS_DEFAULT);
  const [afterParam, setAfterParam] = useState(0);
  const [isLastPageReview, setIsLastPageReview] = useState(true);
  const [userId, setUserId] = useState(0);

  useEffect(() => {
    const stores = reviews.map((review) => review.store);
    setStoresOfReviews(stores);
  }, [reviews]);

  async function fetchAndSetReviews({ saveMethod }: FetchAndSetReviewsParamType) {
    const { lat, lng } = mapPos;
    const after = saveMethod === 'OVERWRITE' ? 0 : afterParam;
    const res = await requestReviewsBasedLocation({ lat, lng, afterParam: after, radius: RADIUS_METER, pagingSize: PAGING_SIZE });

    if (res.code === STATUS_CODE.FAILURE.NOT_EXIST_USER) return;

    setIsLastPageReview(res.data.page.last);
    if (!res.data.page.last) {
      setAfterParam(res.data.page.next);
    }

    switch (saveMethod) {
      case 'OVERWRITE':
        setReviews(res.data.reviews);
        break;
      case 'ACCUMULATE':
        setReviews([...reviews, ...res.data.reviews]);
        break;
      default:
        throw new Error('saveMethod의 값이 잘못되었습니다.');
    }
  }

  async function requestReviewsBasedLocation({ lat, lng, afterParam, radius, pagingSize }: RequestReviewsBasedLocationParamType) {
    const res = await fetchData({ path: `api/users/${userId}/reviews?type=maps&x=${lng}&y=${lat}&radius=${radius}&after=${afterParam}&size=${pagingSize}` });
    return res;
  }

  return { reviews, setReviews, mapPos, setMapPos, fetchAndSetReviews, setAfterParam, isLastPageReview, userId, setUserId, storesOfReviews };
}

type FetchAndSetReviewsParamType = {
  saveMethod: 'OVERWRITE' | 'ACCUMULATE';
};

type RequestReviewsBasedLocationParamType = {
  lat: number;
  lng: number;
  afterParam: number;
  radius: number;
  pagingSize: number;
};
