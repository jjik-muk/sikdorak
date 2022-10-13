import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feeds from 'components/Common/Feeds/Feeds';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useEffect } from 'react';
import { ReviewListWrap } from './ReviewList.styled';

const REVIEW_SIZE = 5;

function ReviewList() {
  const { reviews, dispatchReviews, handleScroll, fetchNextReviews, afterParam } = useReviews();
  useAuth();

  useEffect(() => {
    fetchNextReviews(getUrl(afterParam, REVIEW_SIZE));
  }, [fetchNextReviews, afterParam]);

  return (
    <ReviewListWrap
      onScroll={(e) => {
        handleScroll(e, getUrl(afterParam, REVIEW_SIZE));
      }}
    >
      <CommonHeader dispatchReviews={dispatchReviews} />
      <Feeds reviews={reviews} />
    </ReviewListWrap>
  );

  function getUrl(after, reviewSize) {
    return `api/reviews?after=${after}&size=${reviewSize}`;
  }
}

export default ReviewList;
