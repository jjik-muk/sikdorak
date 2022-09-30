import { Rating } from '@mui/material';
import { useReviewWrite } from 'context/ReviewWriteProvider';

function StarRating() {
  const [reviewWriteState, dispatchReviewWrite] = useReviewWrite();

  const { rating } = reviewWriteState;
  return (
    <Rating
      name="star-rating"
      value={rating}
      onChange={(event, newValue) => {
        handleRating(newValue);
      }}
    />
  );

  function handleRating(idx: number) {
    dispatchReviewWrite({ type: 'RATE', rating: idx });
  }
}

export default StarRating;
