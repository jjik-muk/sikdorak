import Portal from 'common/Portal/Portal';
import Login from 'pages/Login/Login';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import ReviewDetailWithPicture from 'pages/ReviewDetailWithPicture/ReviewDetailWithPicture';
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
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
            path="/writeReview"
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
                <ReviewDetailWithPicture />
              </Portal>
            }
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
