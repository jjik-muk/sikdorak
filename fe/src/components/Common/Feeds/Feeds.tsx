import Feed from 'components/ReviewList/Feed/Feed';
import { Wrap } from './Feeds.styled';

// TODO: FeedsProps 타입 정의
// TODO: 리뷰가 없을 때 적절한 레이아웃 표시

function Feeds({ reviews }: FeedsProps) {
  return (
    <Wrap>
      {reviews ? (
        reviews.map(({ images, like, reviewContent, reviewId, reviewScore, store, user }) => (
          <div key={reviewId}>
            <Feed
              images={images}
              like={like}
              reviewContent={reviewContent}
              reviewId={reviewId}
              reviewScore={reviewScore}
              store={store}
              user={user}
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

type FeedsProps = any;
