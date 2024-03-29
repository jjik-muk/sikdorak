import { MESSAGE } from 'constants/message';
import { FEED } from 'styles/size';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ModeCommentIcon from '@mui/icons-material/ModeComment';
import ShareIcon from '@mui/icons-material/Share';
import { Card, CardActions, CardContent, CardHeader, CardMedia, IconButton, Typography } from '@mui/material';
import KebabMenu from 'components/Common/KebabMenu/KebabMenu';
import RestaurantProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import Rating from 'components/ReviewDetail/TotalRating/Rating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import { openSuccessToast } from 'utils/toast';
import { useSelector } from 'react-redux';
import { RootState } from 'store/modules/store';
import { GET_ALT } from 'constants/alt';
import { TEST_ID } from 'constants/testID';

function FeedCard({ review, isActiveHeart, likeCnt, postLike, toggleIsClikedFeed, isUsedModal, postUnlike }: any) {
  const { user, images, reviewId, reviewScore, reviewContent, store } = review;
  const accountStore = useSelector((state: RootState) => state.account);
  const myUserId = accountStore.id;
  const isMyFeed = user?.userId === myUserId;
  const hasPicture = images.length > 0;

  return (
    <Card onClick={toggleIsClikedFeed} sx={CARD_STYLE} data-testid={TEST_ID.FEED_CARD}>
      <CardHeader
        avatar={<UserProfile nickname={user?.userNickname} imgUrl={user?.userProfileImage} userId={user?.userId} />}
        action={<KebabMenu reviewId={reviewId} isMyFeed={isMyFeed} />}
      />
      {hasPicture && !isUsedModal && <CardMedia component="img" height={FEED.IMG.HEIGHT} image={images[0]} alt={GET_ALT.FOOD(user.userNickname)} />}
      <Rating rating={reviewScore} />
      <CardContent>{reviewContent}</CardContent>
      <RestaurantProfile company={store?.storeName} region={store?.storeAddress} storeId={store?.storeId} />
      <CardActions>
        <IconButton aria-label="like" onClick={isActiveHeart ? handleUnlike : handleLike}>
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
  );

  function handleLike(e) {
    postLike();
    e.stopPropagation();
  }

  function handleUnlike(e) {
    postUnlike();
    e.stopPropagation();
  }

  function handleCopyURL(e) {
    const { clipboard } = navigator;
    const locationOrigin = window.location.origin;
    const reviewUrl = `${locationOrigin}/review/${reviewId}`;
    clipboard.writeText(reviewUrl);
    openSuccessToast(MESSAGE.SUCCESS.SHARE_REVIEW);
    e.stopPropagation();
  }
}

export default FeedCard;

const CARD_STYLE = {
  borderTop: '1px solid rgba(0 0 0 / 20%)',
  overflow: 'visible',
};
