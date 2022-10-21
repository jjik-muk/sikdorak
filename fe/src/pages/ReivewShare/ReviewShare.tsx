import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feed, { ReviewType } from 'components/Common/Feed/Feed';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { fetchData } from 'utils/fetch';

const INIT_STATE: ReviewType = {
  images: [],
  like: { count: 0, userLikeStatus: false },
  reviewContent: '',
  reviewId: 0,
  reviewScore: 0,
  store: { storeId: 0, storeName: '', storeAddress: '' },
  user: { userId: 0, userNickname: '', userProfileImage: '' },
  tags: [],
};

function ReviewShare() {
  const { pathname } = useLocation();
  const Id = Number(pathname.split('/').at(-1));
  const [review, setReview] = useState(INIT_STATE);

  useEffect(() => {
    fetchReview();

    async function fetchReview() {
      const res = await fetchData({ path: `api/reviews/${Id}`, withAccessToken: true });
      setReview(res.data);
    }
  }, [Id]);

  return (
    <Wrap>
      <CommonHeader />
      <FeedWrap>
        <Feed review={review} />
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
  max-width: 600px;
  margin: 20px auto 0 auto;
`;
