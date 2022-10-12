import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset};
  
  * {
    box-sizing: border-box;
  }

  body {
    overflow: hidden;
    font-family: 'Noto Sans KR', sans-serif;
  }

  button {
    margin: 0;
    border: 0;
    padding: 0;
    cursor: pointer;
  }

  a {
    text-decoration: none;
    color: black;
  }
`;

export default GlobalStyle;
