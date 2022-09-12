import Portal from 'components/Common/Portal/Portal';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import ReviewDetailProvider from 'context/ReviewDetailProvider';
import ReviewsProvider from 'context/ReviewsProvider';
import Callback from 'pages/Callback/Callback';
import Login from 'pages/Login/Login';
import Map from 'pages/Map/Map';
import ReviewList from 'pages/ReviewList/ReviewList';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/UserDetail';
import { useEffect, useState } from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import GlobalStyle from 'styles/GlobalStyle';
import { fetchDataThatNeedToLogin } from 'utils/utils';

function App() {
  const [, dispatchMyUserInfo] = useMyUserInfo();
  const [accessToken, setAccessToken] = useState(null);
  const hasAccessToken = () => localStorage.getItem('accessToken');

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
            <Route path="/" element={hasAccessToken() ? <ReviewList /> : <Navigate replace to="/login" />} />
            <Route path="/map" element={hasAccessToken() ? <Map /> : <Navigate replace to="/" />} />
            <Route path="/login" element={hasAccessToken() ? <Navigate replace to="/" /> : <Login />} />
            <Route path="/userDetail">
              <Route path=":userId" element={hasAccessToken() ? <UserDetail /> : <Navigate replace to="/" />} />
            </Route>
            <Route path="/storeDetail" element={hasAccessToken() ? <StoreDetail /> : <Navigate replace to="/" />} />
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
