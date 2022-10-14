import Feed, { FeedProps } from 'components/Common/Feed/Feed';
import GuideText from '../GuideText/GuideText';
import { Wrap } from './Feeds.styled';

// TODO: 리뷰가 없을 때 적절한 레이아웃 표시

function Feeds({ reviews }: FeedsProps) {
  const hasReviews = reviews?.length > 0;

  return (
    <Wrap>
      {hasReviews ? (
        reviews.map(({ images, like, reviewContent, reviewId, reviewScore, store, user, tags }) => (
          <div key={reviewId}>
            <Feed
              images={images}
              like={like}
              reviewContent={reviewContent}
              reviewId={reviewId}
              reviewScore={reviewScore}
              store={store}
              user={user}
              tags={tags}
            />
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
  reviews: FeedProps[];
};
