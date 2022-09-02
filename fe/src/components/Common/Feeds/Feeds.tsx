import Feed from 'components/ReviewList/Feed/Feed';
import { Wrap } from './Feeds.styled';

// TODO: FeedsProps 타입 정의
// TODO: 리뷰가 없을 때 적절한 레이아웃 표시
function Feeds({ reviews }: FeedsProps) {
  return (
    <Wrap>
      {reviews.length ? (
        reviews.map(({ images, reviewContent, reviewId, reviewScore, store, user }) => (
          <Feed
            key={reviewId}
            images={images}
            reviewContent={reviewContent}
            reviewId={reviewId}
            reviewScore={reviewScore}
            store={store}
            user={user}
            likeCnt={0}
          />
        ))
      ) : (
        <div>리뷰가 없습니다.</div>
      )}
    </Wrap>
  );
}

export default Feeds;

type FeedsProps = any;
