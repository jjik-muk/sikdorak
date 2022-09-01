import { DOMAIN, FEEDS } from 'constants/dummyData';
import CommonHeader from 'components/CommonHeader/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import { useEffect, useId, useState } from 'react';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import { ReviewListWrap, Wrap } from './ReviewList.styled';

function ReviewList() {
  const id = useId();
  const [reviews, setReviews] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
  const REVIEW_SIZE = 1;

  useEffect(() => {
    const hasNextPage = afterParam !== 1;
    if (!hasNextPage) return;

    fetchRecommendReviews();

    async function fetchRecommendReviews() {
      const res = await fetchDataThatNeedToLogin(`${DOMAIN}/api/reviews?after=${afterParam}&size=${REVIEW_SIZE}`);
      const resJson = await res.json();
      const nextReviews = resJson.data.reviews;
      const nextAfterParam = resJson.data.page.next;
      setReviews([...reviews, ...nextReviews]);
      setAfterParam(nextAfterParam);
    }
  }, []);

  return (
    <ReviewListWrap onScroll={handleScroll}>
      <CommonHeader />
      <Wrap>
        {FEEDS.map(({ author, contents, pictures, store, likeCnt }, i) => (
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
    </ReviewListWrap>
  );

  function handleScroll(e) {
    const { scrollHeight, scrollTop, clientHeight } = e.target as HTMLDivElement;
    const isScrollEnd = scrollHeight - scrollTop === clientHeight;
    if (isScrollEnd) {
      console.log('끝');
    }
  }
}

export default ReviewList;
