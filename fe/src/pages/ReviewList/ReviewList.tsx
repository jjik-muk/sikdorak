import { DOMAIN } from 'constants/dummyData';
import CommonHeader from 'components/CommonHeader/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import { useEffect, useId, useState } from 'react';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import { Wrap } from './ReviewList.styled';

function ReviewList() {
  const id = useId();
  const [reviews, setReviews] = useState([]);
  const REVIEW_SIZE = 1;

  useEffect(() => {
    fetchRecommendReviews();

    async function fetchRecommendReviews() {
      const res = await fetchDataThatNeedToLogin(`${DOMAIN}/api/reviews?after=0&size=${REVIEW_SIZE}`);
      const resJson = await res.json();
      setReviews(resJson.data);
    }
  }, []);

  return (
    <>
      <CommonHeader />
      <Wrap>
        {reviews &&
          reviews.map(({ author, contents, pictures, store, likeCnt }, i) => (
            <Feed
              key={createKey(id, i)}
              author={author}
              contents={contents}
              pictures={pictures}
              store={store}
              likeCnt={likeCnt}
            />
          ))}
      </Wrap>
    </>
  );
}

export default ReviewList;
