import { DETAIL, FEED } from 'constants/size';
import Icon from 'components/Common/Icon/Icon';
import Carousel from 'components/ReviewDetail/Carousel/Carousel';
import Comment from 'components/ReviewDetail/Comment/Comment';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import WriteComment from 'components/ReviewDetail/WriteComment/WriteComment';
import { FeedProps } from 'components/ReviewList/Feed/Feed';
import TagList from 'components/ReviewWrite/Tag/TagList/TagList';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import { useEffect, useRef, useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { ButtonWrapper, Contents, ContentsWrap, Header, IconWrap, Main, MainFooter, Wrap } from './ReviewDetail.styled';

function ReviewDetail({
  images,
  reviewContent,
  reviewId,
  reviewScore,
  store,
  user,
  isActiveHeart,
  likeCnt,
  postLike,
}: ReviewDetailProps) {
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const hasPicture = images.length > 0;
  const wrapWidth = hasPicture ? DETAIL.WRAP.WIDTH_WITH_IMG : DETAIL.WRAP.WIDTH_NO_IMG;
  const btnWidth = hasPicture ? FEED.BTN.WIDTH_WITH_IMG : FEED.BTN.WIDTH_NO_IMG;
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const [comments, setComments] = useState([]);
  const [afterParam, setAfterParam] = useState(0);
  const COMMENT_SIZE = 2;
  const hasNextComments = afterParam !== 1 && comments.length > 0;
  const menuRef = useRef(null);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  useEffect(() => {
    fetchNextComment();
  }, []);

  return (
    <Wrap>
      {hasPicture && <Carousel urls={images} />}
      <ContentsWrap wrapWidth={wrapWidth}>
        <Header>
          <Profile nickname={user?.userNickname} />
          <div onClick={toggleIsActiveMenu}>
            <Icon icon="MenuBtn" />
            {isActiveMenu && <Menu menuRef={menuRef} reviewId={reviewId} />}
          </div>
        </Header>
        <Main>
          <Contents>{reviewContent}</Contents>
          <Rating rating={reviewScore} />
          <MainFooter>
            <CompnayProfile company={store?.storeName} region={store?.storeAddress} />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <div onClick={postLike}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
              {likeCnt}
            </IconWrap>
          </div>
          <div onClick={focusWrittingComment}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={handleCopyURL}>
            <IconWrap width={btnWidth} height={FEED.BTN.HEIGHT}>
              <Icon icon="ShareArrow" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
        </ButtonWrapper>
        <TagList tags={['#초밥', '#맛집', '#부산']} />
        {comments &&
          comments.map(({ author, content, id }) => <Comment key={id} title={author.nickname} content={content} />)}
        {hasNextComments && (
          <button onClick={fetchNextComment} type="button">
            댓글 더보기
          </button>
        )}
        <WriteComment commentRef={commentRef} reviewId={reviewId} comments={comments} setComments={setComments} />
      </ContentsWrap>
    </Wrap>
  );

  async function fetchNextComment() {
    const commentRes = await fetchDataThatNeedToLogin(
      `api/reviews/${reviewId}/comments?size=${COMMENT_SIZE}&after=${afterParam}`,
    );

    if (!commentRes.data) return;

    const nextComments = commentRes.data.comments;
    const nextAfterParam = commentRes.data.page.next;
    setComments([...comments, ...nextComments]);
    setAfterParam(nextAfterParam);
  }

  function handleCopyURL() {
    const curURL = window.location.href;
    const { clipboard } = navigator;
    clipboard.writeText(curURL);
  }

  function focusWrittingComment() {
    commentRef.current.focus();
  }
}

export default ReviewDetail;

type ReviewDetailProps = FeedProps & {
  isActiveHeart: boolean;
  likeCnt: number;
  postLike: () => void;
};
