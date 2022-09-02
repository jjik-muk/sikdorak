import Feed from 'components/ReviewList/Feed/Feed';
import { Wrap } from './Feeds.styled';

// TODO: FeedsProps 타입 정의
// TODO: 리뷰가 없을 때 적절한 레이아웃 표시
function Feeds({ reviews }: FeedsProps) {
  return (
    <Wrap>
      {reviews.length ? (
        reviews.map(({ reviewId, user, store, images, reviewContent }) => (
          <Feed
            key={reviewId}
            reviewId={reviewId}
            userNickname={user.userNickname}
            contents={reviewContent}
            pictures={images}
            store={store}
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
