import Icon from 'common/Icon';
import styled from 'styled-components';

function TotalRating({ taste, price, service }: { taste: number; price: number; service: number }) {
  return (
    <Wrap>
      <div>맛</div>
      <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={15} height={15} />
      <div>{taste}</div>
      <div>가격</div>
      <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={15} height={15} />
      <div>{price}</div>
      <div>서비스</div>
      <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={15} height={15} />
      <div>{service}</div>
    </Wrap>
  );
}

export default TotalRating;

const Wrap = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 20px;
`;
