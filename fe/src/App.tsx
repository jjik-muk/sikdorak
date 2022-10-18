import Portal from 'components/Common/Portal/Portal';
import ReviewDetailProvider from 'context/ReviewDetailProvider';
import ReviewsProvider from 'context/ReviewsProvider';
import ReviewWriteProvider from 'context/ReviewWriteProvider';
import Callback from 'pages/Callback/Callback';
import Login from 'pages/Login/Login';
import Map from 'pages/Map/Map';
import ReviewShare from 'pages/ReivewShare/ReviewShare';
import ReviewList from 'pages/ReviewList/ReviewList';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/UserDetail';
import { useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import GlobalStyle from 'styles/GlobalStyle';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

function App() {
  useEffect(() => {
    accountStore.setMyInfo();
  }, []);

  return (
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
              <Route path="/api/oauth/callback" element={<Callback />} />
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
        </ReviewWriteProvider>
      </ReviewDetailProvider>
    </ReviewsProvider>
  );
}

export default App;
