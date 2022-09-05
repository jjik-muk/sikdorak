import { DOMAIN } from 'constants/dummyData';
import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import { useEffect, useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { ReviewListWrap } from './ReviewList.styled';

function ReviewList() {
  const [reviews, setReviews] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
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
    const hasNextPage = afterParam !== 1;
    const isScrollEnd = scrollHeight - scrollTop === clientHeight;

    if (hasNextPage && isScrollEnd) {
      fetchNextRecommendReviews();
    }
  }

  async function fetchNextRecommendReviews() {
    const res = await fetchDataThatNeedToLogin(`${DOMAIN}/api/reviews?after=${afterParam}&size=${REVIEW_SIZE}`);
    const nextReviews = res.data.reviews;
    const nextAfterParam = res.data.page.next;
    setReviews([...reviews, ...nextReviews]);
    setAfterParam(nextAfterParam);
  }
}

export default ReviewList;
