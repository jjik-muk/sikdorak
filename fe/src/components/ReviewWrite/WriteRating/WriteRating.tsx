import { Card } from '@mui/material';
import Typography from '@mui/material/Typography';
import StarRating from 'components/ReviewWrite/WriteRating/StarRating/StarRating';
import styled from 'styled-components';

function WriteRating() {
  return (
    <Wrap>
      <Card
        variant="outlined"
        sx={{ display: 'flex', justifyContent: 'space-between', padding: '12px', minHeight: '50px' }}
      >
        <Typography>평점 입력</Typography>
        <StarRating />
      </Card>
    </Wrap>
  );
}

export default WriteRating;

const Wrap = styled.div`
  padding: 12px;
`;
