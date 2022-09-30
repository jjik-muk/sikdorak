import { Card } from '@mui/material';
import Typography from '@mui/material/Typography';
import StarRating from 'components/ReviewWrite/WriteRating/StarRating/StarRating';

function WriteRating() {
  return (
    <Card variant="outlined" sx={{ display: 'flex', justifyContent: 'space-between', padding: '12px' }}>
      <Typography>평점 입력</Typography>
      <StarRating />
    </Card>
  );
}

export default WriteRating;
