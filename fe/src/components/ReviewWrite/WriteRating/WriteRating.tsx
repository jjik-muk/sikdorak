import Typography from '@mui/material/Typography';
import StarRating from 'components/ReviewWrite/WriteRating/StarRating/StarRating';
import { Wrap } from './WriteRating.styled';

function WriteRating() {
  return (
    <Wrap>
      <Typography>평점 입력</Typography>
      <StarRating />
    </Wrap>
  );
}

export default WriteRating;
