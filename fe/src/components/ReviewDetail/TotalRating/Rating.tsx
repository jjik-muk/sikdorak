import Icon from 'components/Common/Icon/Icon';
import { useId } from 'react';
import { createKey } from 'utils/utils';
import { Wrap } from './Rating.styled';

function Rating({ rating }: TotalRatingProps) {
  const yellowStarLen = rating;
  const greyStarLen = 5 - rating;
  const YELLOW = '#f1c40f';
  const GREY = '#bdc3c7';

  return (
    <Wrap>
      <Star length={yellowStarLen} color={YELLOW} />
      <Star length={greyStarLen} color={GREY} />
    </Wrap>
  );
}

function Star({ length, color }: StarProps) {
  const id = useId();

  return (
    <>
      {Array.from({ length }).map((_, i) => (
        <Icon key={createKey(id, i)} icon="Star" stroke="#fff" fill={color} width={15} height={15} />
      ))}
    </>
  );
}

export default Rating;

type TotalRatingProps = {
  rating: number;
};

type StarProps = {
  length: number;
  color: string;
};
