import Callback from 'pages/KakaoOAuthCallback/KakaoOAuthCallback';
import Login from 'pages/Login/Login';
import Map from 'pages/Map/Map';
import ReviewShare from 'pages/ReivewShare/ReviewShare';
import ReviewList from 'pages/ReviewList/ReviewList';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/UserDetail';

const ROUTE_PATH = {
  HOME: '/',
  MAP: '/map',
  LOGIN: '/login',
  USER: '/user/:userId',
  STORE: '/store/:storeId',
  REVIEW: '/review/:reviewId',
  CALLBACK: {
    KAKAO: '/api/oauth/kakao/callback',
  },
} as const;

const routeConfigs: RouteConfig[] = [
  { path: ROUTE_PATH.HOME, element: <ReviewList /> },
  { path: ROUTE_PATH.MAP, element: <Map /> },
  { path: ROUTE_PATH.LOGIN, element: <Login /> },
  { path: ROUTE_PATH.USER, element: <UserDetail /> },
  { path: ROUTE_PATH.STORE, element: <StoreDetail /> },
  { path: ROUTE_PATH.REVIEW, element: <ReviewShare /> },
  { path: ROUTE_PATH.CALLBACK.KAKAO, element: <Callback /> },
];

interface RouteConfig {
  path: string;
  element: React.ReactNode;
}

export { ROUTE_PATH, routeConfigs };
