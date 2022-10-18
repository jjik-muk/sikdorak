import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feeds from 'components/Common/Feeds/Feeds';
import StoreInfo from 'components/StoreDetail/StoreInfo/StoreInfo';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { fetchDataThatNeedToLogin } from 'utils/utils';

const INIT_STATE_STORE_INFO = {
  storeName: '',
  addressName: '',
  contactNumber: '',
  reviewCounts: 0,
  reviewScoreAverage: 0,
};

function StoreDetail() {
  const { pathname } = useLocation();
  const targetId = Number(pathname.split('/').at(-1));
  const [storeInfo, setStoreInfo] = useState(INIT_STATE_STORE_INFO);
  const { storeName, addressName, contactNumber, reviewCounts, reviewScoreAverage } = storeInfo;
  const { reviews, handleScroll, dispatchReviews, fetchNextReviews, afterParam } = useReviews();
  const REVIEW_SIZE = 5;
  useAuth();

  useEffect(() => {
    fetchAndStoreRestaurantInfo();

    async function fetchAndStoreRestaurantInfo() {
      const res = await fetchDataThatNeedToLogin(`api/stores/${targetId}`);
      setStoreInfo(res.data);
    }
  }, [targetId]);

  useEffect(() => {
    const INIT_AFTER_PARAM = 0;
    fetchNextReviews(getUrl(INIT_AFTER_PARAM, REVIEW_SIZE, targetId));
  }, [fetchNextReviews, targetId]);

  return (
    <Wrap
      onScroll={(e) => {
        handleScroll(e, getUrl(afterParam, REVIEW_SIZE, targetId));
      }}
    >
      <CommonHeader dispatchReviews={dispatchReviews} />
      <ContentsWrap>
        <StoreInfo storeName={storeName} storeRating={reviewScoreAverage} reviewCnt={reviewCounts} address={addressName} phoneNumber={contactNumber} />
        <Feeds reviews={reviews} />
      </ContentsWrap>
    </Wrap>
  );
}

function getUrl(after, reviewSize, targetId) {
  return `api/stores/${targetId}/reviews?after=${after}&size=${reviewSize}`;
}

export default StoreDetail;

const Wrap = styled.div`
  width: 100%;
  height: 100vh;
  overflow-y: scroll;
`;

const ContentsWrap = styled.div`
  width: fit-content;
  margin: 0 auto;
`;
