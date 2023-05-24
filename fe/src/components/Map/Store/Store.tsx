import { STYLE } from 'styles/style';
import StarRateIcon from '@mui/icons-material/StarRate';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { fetchData } from 'utils/fetch';
import { useEffect, useState } from 'react';
import { ICON } from 'styles/size';
import Icon from 'components/Common/Icon/Icon';
import { COLOR } from 'styles/color';
import { TEST_ID } from 'constants/testID';

function Store({ store }: any) {
  const navigate = useNavigate();
  const [storeDetail, setStoreDetail] = useState({ reviewScoreAverage: store.reviewScoreAverage, reviewCounts: store.reviewCounts });
  const { id, storeName, contactNumber, roadAddressName } = store;

  useEffect(() => {
    requestStoreDetail();

    async function requestStoreDetail() {
      const res = await fetchData({
        path: `api/stores/${id}`,
      });
      setStoreDetail(res.data);
    }
  }, []);

  return (
    <Wrap
      onClick={() => {
        navigate(`/store/${id}`);
      }}
      data-testid={TEST_ID.STORE}
    >
      <Title>{storeName}</Title>
      <Rate>
        <StarRateIcon color="warning" />
        <Average>{storeDetail.reviewScoreAverage}</Average>
        <div>({storeDetail.reviewCounts})</div>
      </Rate>
      <Row>
        <Icon icon="Phone" width={ICON.MEDIUM} height={ICON.MEDIUM} fill={COLOR.BLACK} />
        <div>{contactNumber}</div>
      </Row>
      <Row>
        <Icon icon="Location" width={ICON.MEDIUM} height={ICON.MEDIUM} fill={COLOR.BLACK} />
        <div>{roadAddressName}</div>
      </Row>
    </Wrap>
  );
}

export default Store;

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  ${STYLE.BOX_CONTAINER}
  padding: 10px;
  cursor: pointer;
`;

const Title = styled.h1`
  font-size: 18px;
  font-weight: 700;
`;

const Rate = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
`;

const Average = styled.div`
  font-weight: 700;
`;

const Row = styled.div`
  display: flex;
  gap: 5px;
  align-items: center;
`;
