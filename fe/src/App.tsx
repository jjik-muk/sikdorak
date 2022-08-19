import Portal from 'common/Portal/Portal';
import Login from 'pages/Login/Login';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
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
          <Route
            path="/reviewDetail1"
            element={
              <Portal selector="#portal">
                <ReviewDetail />
              </Portal>
            }
          />
          <Route
            path="/reviewDetail2"
            element={
              <Portal selector="#portal">
                <ReviewDetail hasPicture />
              </Portal>
            }
          />
          <Route path="/userDetail" element={<UserDetail />} />
          <Route path="/storeDetail" element={<StoreDetail />} />
          <Route path="/reviewList" element={<ReviewList />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
