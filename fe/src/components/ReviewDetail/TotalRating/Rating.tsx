import Icon from 'components/Common/Icon/Icon';
import { useId } from 'react';
import { COLOR } from 'styles/color';
import { ICON } from 'styles/size';
import { createKey } from 'utils/utils';
import { Wrap } from './Rating.styled';

function Rating({ rating }: TotalRatingProps) {
  const yellowStarLen = rating;
  const greyStarLen = 5 - rating;

  return (
    <Wrap data-testid="rating">
      <Star length={yellowStarLen} color={COLOR.YELLOW} />
      <Star length={greyStarLen} color={COLOR.GREY[500]} />
    </Wrap>
  );
}

function Star({ length, color }: StarProps) {
  const id = useId();

  return (
    <>
      {Array.from({ length }).map((_, idx) => (
        <Icon key={createKey(id, idx)} icon="Star" stroke={COLOR.WHITE} fill={color} width={ICON.SMALL} height={ICON.SMALL} />
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
