import Portal from 'components/Common/Portal/Portal';
import useLike from 'hooks/useLike';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef } from 'react';
import styled from 'styled-components';
import FeedCard from '../FeedCard/FeedCard';

function Feed({ review }: { review: ReviewType }) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const { like, reviewId } = review;
  const { isActiveHeart, likeCnt, postLike, postUnlike } = useLike({ like, reviewId });
  const reviewDetailModalRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);

  return (
    <Wrap>
      <FeedCard review={review} isActiveHeart={isActiveHeart} likeCnt={likeCnt} postLike={postLike} postUnlike={postUnlike} toggleIsClikedFeed={toggleIsClikedFeed} />
      {isClikedFeed && (
        <Portal selector="#portal" ref={reviewDetailModalRef}>
          <ReviewDetail review={review} isActiveHeart={isActiveHeart} likeCnt={likeCnt} postLike={postLike} postUnlike={postUnlike} />
        </Portal>
      )}
    </Wrap>
  );
}

export default Feed;

export type ReviewType = {
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
