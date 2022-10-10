import { ICON } from 'constants/size';
import { STYLE } from 'constants/style';
import Icon from 'components/Common/Icon/Icon';
import styled from 'styled-components';

function StoreInfo({ storeName, storeRating, reviewCnt, address, phoneNumber }: any) {
  return (
    <Wrap>
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
    </Wrap>
  );
}

export default StoreInfo;

// type StoreInfoProps = {
//   storeName: string;
//   storeRating: number;
//   reviewCnt: number;
//   address: string;
//   phoneNumber: string;
// };

export const Wrap = styled.div`
  width: 600px;
  height: fit-content;
  padding: 10px;
  ${STYLE.BOX_CONTAINER}
  margin: 20px 0 20px 0;
`;

export const InfoWrap = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const Text = styled.div``;

export const Row = styled.div`
  display: flex;
  gap: 10px;
`;

export const Title = styled.h2`
  font-size: 20px;
  font-weight: 700;
`;
