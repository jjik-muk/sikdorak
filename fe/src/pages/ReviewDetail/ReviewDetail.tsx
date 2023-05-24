import { DETAIL } from 'styles/size';
import { Button } from '@mui/material';
import FeedCard from 'components/Common/FeedCard/FeedCard';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import TagList from 'components/ReviewDetail/TagList/TagList';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import useAuth from 'hooks/useAuth';
import { useEffect, useRef } from 'react';
import { ContentsWrap, Wrap } from './ReviewDetail.styled';
import useComment from 'hooks/useComment';
import { TEST_ID } from 'constants/testID';

function ReviewDetail({ review, isActiveHeart, likeCnt, postLike, postUnlike }: any) {
  const { images, reviewId, user, tags } = review;
  const commentRef = useRef<HTMLInputElement>(null);
  const hasPicture = images.length > 0;
  const wrapWidth = hasPicture ? DETAIL.WRAP.WIDTH_WITH_IMG : DETAIL.WRAP.WIDTH_NO_IMG;
  useAuth();
  const { fetchAndSetComments, hasNextComments, comments, setComments } = useComment({ reviewId });

  useEffect(function initComments() {
    fetchAndSetComments({ saveMethod: 'OVERWRITE' });
  }, []);

  return (
    <Wrap data-testid={TEST_ID.REVIEW_DETAIL}>
      {hasPicture && <Carousel urls={images} />}
      <ContentsWrap wrapWidth={wrapWidth}>
        <FeedCard review={review} isActiveHeart={isActiveHeart} likeCnt={likeCnt} postLike={postLike} postUnlike={postUnlike} isUsedModal />
        <div style={{ padding: '0 10px 10px 10px' }}>
          {Boolean(tags.length) && <TagList tags={tags} imgUrl={user.userProfileImage} />}
          {comments &&
            comments.map(({ author, content, id }) => (
              <Comment
                key={id}
                commentId={id}
                reviewId={reviewId}
                authorId={author.id}
                title={author.nickname}
                content={content}
                imgUrl={author.profileImage}
                comments={comments}
                setComments={setComments}
              />
            ))}
          {hasNextComments && <Button onClick={handleMoreBtn}>댓글 더보기</Button>}
          <WriteComment commentRef={commentRef} reviewId={reviewId} fetchAndSetComments={fetchAndSetComments} />
        </div>
      </ContentsWrap>
    </Wrap>
  );

  function handleMoreBtn() {
    fetchAndSetComments({ saveMethod: 'ACCUMULATE' });
  }
}

export default ReviewDetail;
