import Carousel from 'components/Carousel/Carousel';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { Wrap } from './ReviewDetailWithPicture.styled';

function ReviewDetailWithPicture() {
  return (
    <Wrap>
      <Carousel />
      <ReviewDetail />
    </Wrap>
  );
}

export default ReviewDetailWithPicture;
