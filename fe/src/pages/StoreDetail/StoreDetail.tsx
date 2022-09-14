import CommonHeader from 'components/Common/Header/CommonHeader';
import StoreInfo from 'components/StoreDetail/StoreInfo/StoreInfo';
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

  useEffect(() => {
    fetchAndStoreRestaurant();

    async function fetchAndStoreRestaurant() {
      const res = await fetchDataThatNeedToLogin(`api/stores/${targetId}`);
      setStoreInfo(res.data);
    }
  }, []);

  const { storeName, addressName, contactNumber, reviewCounts, reviewScoreAverage } = storeInfo;

  return (
    <>
      <CommonHeader />
      <Wrap>
        <StoreInfo
          storeName={storeName}
          storeRating={reviewScoreAverage}
          reviewCnt={reviewCounts}
          address={addressName}
          phoneNumber={contactNumber}
        />
      </Wrap>
    </>
  );
}

export default StoreDetail;

const Wrap = styled.div`
  width: fit-content;
  margin: 0 auto;
`;
