import { STORE } from 'constants/dummyData';
import { ICON } from 'constants/size';
import CommonHeader from 'components/Common/Header/CommonHeader';
import Icon from 'components/Common/Icon/Icon';
import { InfoWrap, Row, StoreInfo, Text, Title, Wrap } from './StoreDetail.styled';

function StoreDetail() {
  const { storeName, storeRating, reviewCnt, address, phoneNumber } = STORE;

  return (
    <>
      <CommonHeader />
      <Wrap>
        <StoreInfo>
          <InfoWrap>
            <Title>{storeName}</Title>
            <Row>
              <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={ICON.SMALL} height={ICON.SMALL} />
              <Text>{storeRating} / 5</Text>
              <Text>리뷰 {reviewCnt}</Text>
            </Row>
            <Row>
              <Icon icon="Location" width={ICON.SMALL} height={ICON.SMALL} fill="#000" />
              <Text>{address}</Text>
            </Row>
            <Row>
              <Icon icon="Phone" width={ICON.SMALL} height={ICON.SMALL} fill="#000" />
              <Text>{phoneNumber}</Text>
            </Row>
          </InfoWrap>
        </StoreInfo>
      </Wrap>
    </>
  );
}

export default StoreDetail;
