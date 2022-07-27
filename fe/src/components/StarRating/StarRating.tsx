import Icon from 'common/Icon';
import { useState } from 'react';
import { Wrap } from './StarRating.style';

const YELLOW = '#f1c40f';

function StarRating() {
  const [rating, setRating] = useState(5);

  return (
    <Wrap>
      {[...Array(rating)].map((_, i) => (
        <Icon
          icon="Star"
          fill={YELLOW}
          onClick={() => {
            handleRating(i + 1);
          }}
        />
      ))}
      {[...Array(5 - rating)].map((_, i) => (
        <Icon
          icon="Star"
          fill="none"
          onClick={() => {
            handleRating(i + 1 + rating);
          }}
        />
      ))}
    </Wrap>
  );

  function handleRating(idx: number) {
    setRating(idx);
  }
}

export default StarRating;
