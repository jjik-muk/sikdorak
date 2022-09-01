import StarIcon from 'components/ReviewWrite/WriteRating/StarRating/StarIcon/StarIcon';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { createKey } from 'utils/utils';
import { Wrap } from './StarRating.styled';

const YELLOW = '#f1c40f';

function StarRating() {
  const [reviewWriteState, dispatchReviewWrite] = useReviewWrite();

  const { rating } = reviewWriteState;
  return (
    <Wrap>
      {[...Array(rating)].map((_, i) => (
        <StarIcon
          key={createKey(_, i)}
          onClick={() => {
            handleRating(i + 1);
          }}
          fill={YELLOW}
        />
      ))}
      {[...Array(5 - rating)].map((_, i) => (
        <StarIcon
          key={createKey(_, i)}
          onClick={() => {
            handleRating(i + 1 + rating);
          }}
          fill="none"
        />
      ))}
    </Wrap>
  );

  function handleRating(idx: number) {
    dispatchReviewWrite({ type: 'RATE', rating: idx });
  }
}

export default StarRating;
