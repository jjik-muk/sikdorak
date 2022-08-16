import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset};
  
  *{
    box-sizing: border-box;
  }

  button {
    margin: 0;
    border: 0;
    padding: 0;
    cursor: pointer;
  }
`;

export default GlobalStyle;
