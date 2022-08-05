import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

const Header = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')}
`;

const Main = styled.div`
  border-radius: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

const MainFooter = styled.div`
  border-top: 1px solid #000;
`;

const ButtonWrapper = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  margin: 30px 0;
`;

export { Header, Main, MainFooter, ButtonWrapper };
