import { IconComponentsKeys } from 'components/Common/Icon/Icon';

const TEST_ID = {
  FEED_CARD: 'feed-card',
  FEED: 'feed',
  KEBAB_MENU: 'kebab-menu',
  LOADING: 'spinner-wrap',
  LOGIN_INPUT: 'login-input',
  RESTAURANT_PROFILE: 'restaurant-profile',
  RATING: 'rating',
  REVIEW_DETAIL: 'review-detail',
  STORE: 'store',
};

const GET_TEST_ID = {
  ICON: (icon: IconComponentsKeys) => `${icon}-icon`,
};

export { TEST_ID, GET_TEST_ID };
