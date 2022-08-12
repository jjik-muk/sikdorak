import Portal from 'common/Portal/Portal';
import Login from 'pages/Login/Login';
import ReviewDetailWithPicture from 'pages/ReviewDetail/ReviewDetail';
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
                <ReviewDetailWithPicture />
              </Portal>
            }
          />
          <Route
            path="/reviewDetail2"
            element={
              <Portal selector="#portal">
                <ReviewDetailWithPicture hasPicture />
              </Portal>
            }
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
