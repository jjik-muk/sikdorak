import Login from 'pages/Login/Login';
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
          <Route path="/write-review" element={<ReviewWrite />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
