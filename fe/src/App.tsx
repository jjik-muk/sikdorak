import ReviewDetailProvider from 'context/ReviewDetailProvider';
import ReviewsProvider from 'context/ReviewsProvider';
import ReviewWriteProvider from 'context/ReviewWriteProvider';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import GlobalStyle from 'styles/GlobalStyle';
import { ToastContainer } from 'react-toastify';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import 'react-toastify/dist/ReactToastify.css';
import { routeConfigs } from 'constants/route';
import { nanoid } from '@reduxjs/toolkit';

function App() {
  return (
    <>
      <ReviewsProvider>
        <ReviewDetailProvider>
          <ReviewWriteProvider>
            <GlobalStyle />
            <BrowserRouter>
              <Routes>
                {routeConfigs.map(({ path, element }) => (
                  <Route key={nanoid()} path={path} element={element} />
                ))}
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
