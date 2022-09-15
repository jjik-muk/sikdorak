import Portal from 'components/Common/Portal/Portal';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import ReviewDetailProvider from 'context/ReviewDetailProvider';
import ReviewsProvider from 'context/ReviewsProvider';
import Callback from 'pages/Callback/Callback';
import Login from 'pages/Login/Login';
import Map from 'pages/Map/Map';
import ReviewShare from 'pages/ReivewShare/ReviewShare';
import ReviewList from 'pages/ReviewList/ReviewList';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/UserDetail';
import { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import GlobalStyle from 'styles/GlobalStyle';
import { fetchDataThatNeedToLogin } from 'utils/utils';

function App() {
  const [, dispatchMyUserInfo] = useMyUserInfo();
  const [accessToken, setAccessToken] = useState(null);

  useEffect(() => {
    setMyInfo();

    async function setMyInfo() {
      const myInfo = await fetchDataThatNeedToLogin(`api/users/me`);
      const { id, nickname, profileImage } = myInfo.data || { id: null, nickname: null, profileImage: null };
      const myInfoJson = JSON.stringify({ userId: id, nickname, profileImageUrl: profileImage });
      localStorage.setItem('MY_INFO', myInfoJson);
      dispatchMyUserInfo({ type: 'SET_USER', userId: id, nickname, profileImageUrl: profileImage });
    }
  }, [accessToken, dispatchMyUserInfo]);

  return (
    <ReviewsProvider>
      <ReviewDetailProvider>
        <GlobalStyle />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<ReviewList />} />
            <Route path="/map" element={<Map />} />
            <Route path="/login" element={<Login />} />
            <Route path="/user">
              <Route path=":userId" element={<UserDetail />} />
            </Route>
            <Route path="/store">
              <Route path=":storeId" element={<StoreDetail />} />
            </Route>
            <Route path="/review">
              <Route path=":reviewId" element={<ReviewShare />} />
            </Route>
            <Route
              path="/api/oauth/callback"
              element={<Callback accessToken={accessToken} setAccessToken={setAccessToken} />}
            />
            <Route
              path="/reviewWrite"
              element={
                <Portal selector="#portal">
                  <ReviewWrite />
                </Portal>
              }
            />
          </Routes>
        </BrowserRouter>
      </ReviewDetailProvider>
    </ReviewsProvider>
  );
}

export default App;
