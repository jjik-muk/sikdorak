import { STORE } from 'constants/dummyData';
import CommonHeader from 'components/Common/Header/CommonHeader';
import StoreInfo from 'components/StoreDetail/StoreInfo/StoreInfo';
import styled from 'styled-components';

function StoreDetail() {
  const { storeName, storeRating, reviewCnt, address, phoneNumber } = STORE;

  return (
    <>
      <CommonHeader />
      <Wrap>
        <StoreInfo
          storeName={storeName}
          storeRating={storeRating}
          reviewCnt={reviewCnt}
          address={address}
          phoneNumber={phoneNumber}
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
