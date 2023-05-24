import Feed, { ReviewType } from 'components/Common/Feed/Feed';
import GuideText from '../GuideText/GuideText';
import { Wrap } from './Feeds.styled';
import { createKey } from 'utils/utils';
import { TEST_ID } from 'constants/testID';

function Feeds({ reviews }: FeedsProps) {
  const hasReviews = reviews?.length > 0;
  return (
    <Wrap>
      {hasReviews ? (
        <div data-testid={TEST_ID.FEED}>
          {reviews.map((review) => (
            <div key={createKey('feed', review.reviewId)}>
              <Feed review={review} />
            </div>
          ))}
        </div>
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
