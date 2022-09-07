import { DOMAIN } from 'constants/dummyData';
import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import { useReviews } from 'context/ReviewsProvider';
import { useEffect, useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { ReviewListWrap } from './ReviewList.styled';

function ReviewList() {
  const [{ reviews }, dispatchReviews] = useReviews();
  const [afterParam, setAfterParam] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);
  const REVIEW_SIZE = 5;

  useEffect(() => {
    fetchNextRecommendReviews();
  }, []);

  return (
    <ReviewListWrap onScroll={handleScroll}>
      <CommonHeader />
      <Feeds reviews={reviews} />
    </ReviewListWrap>
  );

  function handleScroll(e) {
    const { scrollHeight, scrollTop, clientHeight } = e.target as HTMLDivElement;
    const isScrollEnd = scrollHeight - scrollTop === clientHeight;

    if (hasNextPage && isScrollEnd) {
      fetchNextRecommendReviews();
    }
  }

  async function fetchNextRecommendReviews() {
    const res = await fetchDataThatNeedToLogin(`${DOMAIN}/api/reviews?after=${afterParam}&size=${REVIEW_SIZE}`);
    const nextReviews = res.data.reviews;
    const nextAfterParam = res.data.page.next;
    dispatchReviews({ type: 'ADD_REVIEWS', reviews: nextReviews });
    setAfterParam(nextAfterParam);

    if (res.data.page.last) {
      setHasNextPage(false);
    }
  }
}

export default ReviewList;
