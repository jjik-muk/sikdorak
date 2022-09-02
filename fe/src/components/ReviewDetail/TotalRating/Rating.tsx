import Icon from 'components/Common/Icon/Icon';
import { Wrap } from './Rating.styled';

function Rating({ rating }: TotalRatingProps) {
  return (
    <Wrap>
      {Array.from({ length: rating }).map(() => (
        <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={15} height={15} />
      ))}
    </Wrap>
  );
}

export default Rating;

type TotalRatingProps = {
  rating: number;
};
