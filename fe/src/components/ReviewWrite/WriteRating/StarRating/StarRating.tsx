import StarIcon from 'components/ReviewWrite/WriteRating/StarRating/StarIcon/StarIcon';
import { useState } from 'react';
import { Wrap } from './StarRating.styled';

const YELLOW = '#f1c40f';

function StarRating() {
  const [rating, setRating] = useState(0);

  return (
    <Wrap>
      {[...Array(rating)].map((_, i) => (
        <StarIcon
          onClick={() => {
            handleRating(i + 1);
          }}
          fill={YELLOW}
        />
      ))}
      {[...Array(5 - rating)].map((_, i) => (
        <StarIcon
          onClick={() => {
            handleRating(i + 1 + rating);
          }}
          fill="none"
        />
      ))}
    </Wrap>
  );

  function handleRating(idx: number) {
    setRating(idx);
  }
}

export default StarRating;
