import Portal from 'common/Portal/Portal';
import Callback from 'pages/Callback/Callback';
import Login from 'pages/Login/Login';
import ReviewList from 'pages/ReviewList/ReviewList';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import StoreDetail from 'pages/StoreDetail/StoreDetail';
import UserDetail from 'pages/UserDetail/userDetail';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import GlobalStyle from 'styles/GlobalStyle';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route
            path="/reviewWrite"
            element={
              <Portal selector="#portal">
                <ReviewWrite />
              </Portal>
            }
          />
          <Route path="/userDetail" element={<UserDetail />} />
          <Route path="/storeDetail" element={<StoreDetail />} />
          <Route path="/reviewList" element={<ReviewList />} />
          <Route path="/api/oauth/callback" element={<Callback />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
