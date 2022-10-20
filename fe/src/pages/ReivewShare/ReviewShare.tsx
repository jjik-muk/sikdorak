import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feed from 'components/Common/Feed/Feed';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { fetchData } from 'utils/fetch';

const INIT_STATE: any = {};

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
  width: fit-content;
  margin: 10px auto;
`;
