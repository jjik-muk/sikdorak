import BoxContainer from 'components/BoxContainer/BoxContainer';
import StarRating from 'components/StarRating/StarRating';
import { Text, Wrap } from './WriteRating.styled';

function WriteRating() {
  return (
    <BoxContainer>
      <Wrap>
        <Text>평점 입력</Text>
        <StarRating />
      </Wrap>
    </BoxContainer>
  );
}

export default WriteRating;
