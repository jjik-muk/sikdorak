import { STYLE } from 'styles/style';
import StarRateIcon from '@mui/icons-material/StarRate';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

function Store({ id, storeName, contactNumber, roadAddressName, reviewCounts, reviewScoreAverage }: any) {
  const navigate = useNavigate();
  return (
    <Wrap
      onClick={() => {
        navigate(`/store/${id}`);
      }}
    >
      <Title>{storeName}</Title>
      <Rate>
        <StarRateIcon color="warning" />
        <Average>{reviewScoreAverage}</Average>
        <div>({reviewCounts})</div>
      </Rate>
      <div>{contactNumber}</div>
      <div>{roadAddressName}</div>
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
  font-size: large;
  font-weight: 700;
`;

const Rate = styled.div`
  display: flex;
  align-items: center;
  gap: 2px;
`;

const Average = styled.div`
  font-weight: 700;
`;
