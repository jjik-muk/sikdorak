import { DOMAIN } from 'constants/dummyData';
import CommonHeader from 'components/CommonHeader/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import { useEffect, useId, useState } from 'react';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import { ReviewListWrap, Wrap } from './ReviewList.styled';

function ReviewList() {
  const id = useId();
  const [reviews, setReviews] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
  const REVIEW_SIZE = 5;

  useEffect(() => {
    fetchNextRecommendReviews();
  }, []);

  return (
    <ReviewListWrap onScroll={handleScroll}>
      <CommonHeader />
      <Wrap>
        {reviews &&
          reviews.map(({ reviewId, user, store, images, reviewContent }, i) => (
            <Feed
              key={createKey(id, i)}
              reviewId={reviewId}
              userNickname={user.userNickname}
              contents={reviewContent}
              pictures={images}
              store={store}
              likeCnt={0}
            />
          ))}
      </Wrap>
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
