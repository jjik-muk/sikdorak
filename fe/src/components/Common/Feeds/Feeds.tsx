import Feed, { FeedProps } from 'components/ReviewList/Feed/Feed';
import { Wrap } from './Feeds.styled';

// TODO: 리뷰가 없을 때 적절한 레이아웃 표시

function Feeds({ reviews, isUsedMapPage }: FeedsProps) {
  const hasReviews = reviews.length > 0;

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
              isUsedMapPage={isUsedMapPage}
            />
          </div>
        ))
      ) : (
        <div>리뷰가 없습니다.</div>
      )}
    </Wrap>
  );
}

export default Feeds;

type FeedsProps = {
  reviews: FeedProps[];
  isUsedMapPage?: boolean;
};
