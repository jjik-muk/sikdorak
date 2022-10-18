import Feed, { ReviewType } from 'components/Common/Feed/Feed';
import GuideText from '../GuideText/GuideText';
import { Wrap } from './Feeds.styled';

// TODO: 리뷰가 없을 때 적절한 레이아웃 표시

function Feeds({ reviews }: FeedsProps) {
  const hasReviews = reviews?.length > 0;

  return (
    <Wrap>
      {hasReviews ? (
        reviews.map((review) => (
          <div key={review.reviewId}>
            <Feed review={review} />
          </div>
        ))
      ) : (
        <GuideText text="리뷰가 없습니다." />
      )}
    </Wrap>
  );
}

export default Feeds;

type FeedsProps = {
  reviews: ReviewType[];
};
