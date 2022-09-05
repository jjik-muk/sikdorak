import Feed from 'components/ReviewList/Feed/Feed';
import { Wrap } from './Feeds.styled';

// TODO: FeedsProps 타입 정의
// TODO: 리뷰가 없을 때 적절한 레이아웃 표시

function Feeds({ reviews }: FeedsProps) {
  console.log('reviews', reviews);
  return (
    <Wrap>
      {reviews ? (
        reviews.map(({ images, reviewContent, id, reviewScore, store, user }) => (
          <div key={id}>
            <Feed
              images={images}
              reviewContent={reviewContent}
              reviewId={id}
              reviewScore={reviewScore}
              store={store}
              user={user}
              likeCnt={0}
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
