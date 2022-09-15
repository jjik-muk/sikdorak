import CommonHeader from 'components/Common/Header/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { fetchDataThatNeedToLogin } from 'utils/utils';

const INIT_STATE: any = {};

function ReviewShare() {
  const { pathname } = useLocation();
  const Id = Number(pathname.split('/').at(-1));
  const [review, setReview] = useState(INIT_STATE);
  const { images, like, reviewContent, reviewId, reviewScore, store, user, tags } = review;

  useEffect(() => {
    fetchReview();

    async function fetchReview() {
      const res = await fetchDataThatNeedToLogin(`api/reviews/${Id}`);
      setReview(res.data);
    }
  }, []);

  return (
    <Wrap>
      <CommonHeader />
      <FeedWrap>
        <Feed
          images={images}
          like={like}
          reviewContent={reviewContent}
          reviewId={reviewId}
          reviewScore={reviewScore}
          store={store}
          user={user}
          tags={tags}
        />
      </FeedWrap>
    </Wrap>
  );
}

export default ReviewShare;

const Wrap = styled.div`
  width: 100%;
  height: 100vh;
`;

const FeedWrap = styled.div`
  width: fit-content;
  margin: 10px auto;
`;
