import { DETAIL } from 'styles/size';
import { Button } from '@mui/material';
import FeedCard from 'components/Common/FeedCard/FeedCard';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import TagList from 'components/ReviewDetail/TagList/TagList';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import useAuth from 'hooks/useAuth';
import { useEffect, useRef, useState } from 'react';
import { fetchData } from 'utils/fetch';
import { ContentsWrap, Wrap } from './ReviewDetail.styled';

function ReviewDetail({ review, isActiveHeart, likeCnt, postLike, postUnlike }: any) {
  const { images, reviewId, user, tags } = review;
  const commentRef = useRef<HTMLInputElement>(null);
  const hasPicture = images.length > 0;
  const wrapWidth = hasPicture ? DETAIL.WRAP.WIDTH_WITH_IMG : DETAIL.WRAP.WIDTH_NO_IMG;
  const [comments, setComments] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
  const COMMENT_SIZE = 2;
  const [hasNextComments, setHasNextComments] = useState(false);
  useAuth();

  useEffect(() => {
    fetchNextComment();
  }, []);

  return (
    <Wrap>
      {hasPicture && <Carousel urls={images} />}
      <ContentsWrap wrapWidth={wrapWidth}>
        <FeedCard review={review} isActiveHeart={isActiveHeart} likeCnt={likeCnt} postLike={postLike} postUnlike={postUnlike} isUsedModal />
        <div style={{ padding: '0 10px 10px 10px' }}>
          {Boolean(tags.length) && <TagList tags={tags} imgUrl={user.userProfileImage} />}
          {comments && comments.map(({ author, content, id }) => <Comment key={id} authorId={author.id} title={author.nickname} content={content} imgUrl={author.profileImage} />)}
          {hasNextComments && <Button onClick={fetchNextComment}>댓글 더보기</Button>}
          <WriteComment commentRef={commentRef} reviewId={reviewId} comments={comments} setComments={setComments} />
        </div>
      </ContentsWrap>
    </Wrap>
  );

  async function fetchNextComment() {
    const commentRes = await fetchData({ path: `api/reviews/${reviewId}/comments?size=${COMMENT_SIZE}&after=${afterParam}` });
    setHasNextComments(!commentRes.data.page.last);
    if (!commentRes.data) return;

    const nextComments = commentRes.data.comments;
    const nextAfterParam = commentRes.data.page.next;
    setComments([...comments, ...nextComments]);
    setAfterParam(nextAfterParam);
  }
}

export default ReviewDetail;
