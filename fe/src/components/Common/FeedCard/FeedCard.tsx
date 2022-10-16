import FavoriteIcon from '@mui/icons-material/Favorite';
import ModeCommentIcon from '@mui/icons-material/ModeComment';
import ShareIcon from '@mui/icons-material/Share';
import { Card, CardActions, CardContent, CardHeader, CardMedia, IconButton, Typography } from '@mui/material';
import KebabMenu from 'components/Common/KebabMenu/KebabMenu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import { observer } from 'mobx-react';
import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';

const FeedCard = observer(({ images, reviewContent, reviewId, reviewScore, store, user, isActiveHeart, likeCnt, postLike, toggleIsClikedFeed, isUsedModal }: any) => {
  const [copyText, setCopyText] = useState('');
  const myUserId = accountStore.id;
  const isMyFeed = user?.userId === myUserId;
  const location = useLocation();
  const hasPicture = images.length > 0;

  return (
    <Card onClick={toggleIsClikedFeed} sx={CARD_STYLE}>
      <CardHeader
        avatar={<UserProfile nickname={user?.userNickname} imgUrl={user?.userProfileImage} userId={user?.userId} />}
        action={<KebabMenu reviewId={reviewId} isMyFeed={isMyFeed} />}
      />
      {hasPicture && !isUsedModal && <CardMedia component="img" height="194" image={images[0]} alt="User profile" />}
      <Rating rating={reviewScore} />
      <CardContent>{reviewContent}</CardContent>
      <CompnayProfile company={store?.storeName} region={store?.storeAddress} storeId={store?.storeId} />
      <CardActions>
        <IconButton aria-label="like" onClick={handleLike}>
          <FavoriteIcon color={isActiveHeart ? 'warning' : 'action'} />
          <Typography>{likeCnt}</Typography>
        </IconButton>
        {accountStore.id && (
          <IconButton aria-label="comment">
            <ModeCommentIcon />
          </IconButton>
        )}
        <IconButton aria-label="share" onClick={handleCopyURL}>
          <ShareIcon />
        </IconButton>
      </CardActions>
    </Card>
  );

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
});

export default FeedCard;

const CARD_STYLE = {
  borderTop: '1px solid rgba(0 0 0 / 20%)',
  overflow: 'visible',
};
