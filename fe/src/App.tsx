import ReviewDetailProvider from 'context/ReviewDetailProvider';
import ReviewsProvider from 'context/ReviewsProvider';
import ReviewWriteProvider from 'context/ReviewWriteProvider';
import Callback from 'pages/KakaoOAuthCallback/KakaoOAuthCallback';
import Login from 'pages/Login/Login';
import Map from 'pages/Map/Map';
import ReviewShare from 'pages/ReivewShare/ReviewShare';
import ReviewList from 'pages/ReviewList/ReviewList';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/UserDetail';
import { useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import GlobalStyle from 'styles/GlobalStyle';
import { ToastContainer } from 'react-toastify';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  useEffect(() => {
    accountStore.setMyInfo();
  }, []);

  return (
    <>
      <ReviewsProvider>
        <ReviewDetailProvider>
          <ReviewWriteProvider>
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
                <Route path="/api/oauth/kakao/callback" element={<Callback />} />
              </Routes>
            </BrowserRouter>
          </ReviewWriteProvider>
        </ReviewDetailProvider>
      </ReviewsProvider>
      <ToastContainer />
    </>
  );
}

export default App;
