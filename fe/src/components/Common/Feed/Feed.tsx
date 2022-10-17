import Portal from 'components/Common/Portal/Portal';
import useLike from 'hooks/useLike';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef } from 'react';
import styled from 'styled-components';
import FeedCard from '../FeedCard/FeedCard';

function Feed({ images, like = { count: 0, userLikeStatus: false }, reviewContent, reviewId, reviewScore, store, user, tags }: FeedProps) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const { isActiveHeart, likeCnt, postLike, postUnlike } = useLike({ like, reviewId });
  const reviewDetailModalRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);

  return (
    <Wrap>
      <FeedCard
        images={images}
        reviewContent={reviewContent}
        reviewId={reviewId}
        reviewScore={reviewScore}
        store={store}
        user={user}
        isActiveHeart={isActiveHeart}
        likeCnt={likeCnt}
        postLike={postLike}
        postUnlike={postUnlike}
        toggleIsClikedFeed={toggleIsClikedFeed}
      />
      {isClikedFeed && (
        <Portal selector="#portal" ref={reviewDetailModalRef}>
          <ReviewDetail
            images={images}
            like={like}
            reviewContent={reviewContent}
            reviewId={reviewId}
            reviewScore={reviewScore}
            store={store}
            user={user}
            isActiveHeart={isActiveHeart}
            likeCnt={likeCnt}
            postLike={postLike}
            postUnlike={postUnlike}
            tags={tags}
          />
        </Portal>
      )}
    </Wrap>
  );
}

export default Feed;

export type FeedProps = {
  images: string[];
  like: { count: number; userLikeStatus: boolean };
  reviewContent: string;
  reviewId: number;
  reviewScore: number;
  store: { storeId: number; storeName: string; storeAddress: string };
  user: { userId: number; userNickname: string; userProfileImage: string };
  tags: string[];
};

const Wrap = styled.div`
  max-width: 600px;

  @media (max-width: 600px) {
    max-width: 100%;
  }
`;
