import { Rating } from '@mui/material';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { Wrap } from './StarRating.styled';

function StarRating() {
  const [reviewWriteState, dispatchReviewWrite] = useReviewWrite();

  const { rating } = reviewWriteState;
  return (
    <Wrap>
      <Rating
        name="star-rating"
        value={rating}
        onChange={(event, newValue) => {
          handleRating(newValue);
        }}
      />
    </Wrap>
  );

  function handleRating(idx: number) {
    dispatchReviewWrite({ type: 'RATE', rating: idx });
  }
}

export default StarRating;
