import FavoriteIcon from '@mui/icons-material/Favorite';
import ModeCommentIcon from '@mui/icons-material/ModeComment';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import ShareIcon from '@mui/icons-material/Share';
import { Card, CardActions, CardContent, CardHeader, CardMedia, IconButton, Typography } from '@mui/material';
import Portal from 'components/Common/Portal/Portal';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import useLike from 'hooks/useLike';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import styled from 'styled-components';

function Feed({
  images,
  like = { count: 0, userLikeStatus: false },
  reviewContent,
  reviewId,
  reviewScore,
  store,
  user,
  tags,
}: FeedProps) {
  const [isClikedFeed, toggleIsClikedFeed] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const { isActiveHeart, likeCnt, postLike } = useLike({ like, reviewId });
  const [copyText, setCopyText] = useState('');

  const reviewDetailModalRef = useRef(null);
  useOutsideClick(reviewDetailModalRef, toggleIsClikedFeed);
  const menuRef = useRef(null);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  const myUserId = accountStore.id;
  const isMyFeed = user?.userId === myUserId;
  const location = useLocation();

  useEffect(() => {
    const hostUrl = window.location.href.replace(location.pathname, '');
    setCopyText(`${hostUrl}/review/${reviewId}`);
  }, []);

  return (
    <Wrap>
      <Card onClick={toggleIsClikedFeed}>
        <CardHeader
          avatar={<UserProfile nickname={user?.userNickname} imgUrl={user?.userProfileImage} userId={user?.userId} />}
          action={
            isMyFeed && (
              <div onClick={handleMenu}>
                <IconButton aria-label="settings">
                  <MoreVertIcon />
                </IconButton>
                {isActiveMenu && <Menu menuRef={menuRef} reviewId={reviewId} />}
              </div>
            )
          }
        />
        <CardMedia component="img" height="194" image={images[0]} alt="picture of user" />
        <Rating rating={reviewScore} />
        <CardContent>{reviewContent}</CardContent>
        <CompnayProfile company={store?.storeName} region={store?.storeAddress} storeId={store?.storeId} />
        <CardActions>
          <IconButton aria-label="like" onClick={handleLike}>
            <FavoriteIcon color={isActiveHeart ? 'warning' : 'action'} />
            <Typography>{likeCnt}</Typography>
          </IconButton>
          <IconButton aria-label="comment">
            <ModeCommentIcon />
          </IconButton>
          <IconButton aria-label="share" onClick={handleCopyURL}>
            <ShareIcon />
          </IconButton>
        </CardActions>
      </Card>
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
            tags={tags}
          />
        </Portal>
      )}
    </Wrap>
  );

  function handleMenu(e) {
    toggleIsActiveMenu();
    e.stopPropagation();
  }

  function handleLike(e) {
    postLike();
    e.stopPropagation();
  }

  function handleCopyURL(e) {
    const { clipboard } = navigator;
    const hostUrl = window.location.href.replace(location.pathname, '');
    setCopyText(`${hostUrl}/review/${reviewId}`);
    clipboard.writeText(copyText);
    alert('공유할 리뷰 페이지가 복사되었습니다.');
    e.stopPropagation();
  }
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
