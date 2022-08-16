import StarRating from 'components/ReviewWrite/WriteRating/StarRating/StarRating';
import { Text, Wrap } from './WriteRating.styled';

function WriteRating() {
  return (
    <Wrap>
      <Text>평점 입력</Text>
      <StarRating />
    </Wrap>
  );
}

export default WriteRating;
