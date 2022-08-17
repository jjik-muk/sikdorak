import Icon from 'common/Icon';
import { Wrap } from './TotalRating.styled';

function TotalRating({ taste, price, service }: TotalRatingProps) {
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

type TotalRatingProps = {
  taste: number;
  price: number;
  service: number;
};
