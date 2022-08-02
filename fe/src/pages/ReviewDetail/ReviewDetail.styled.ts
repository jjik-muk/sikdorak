import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

const Header = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')}
`;

const Main = styled.div`
  border-radius: 10px;
`;

const MainFooter = styled.div`
  border-top: 1px solid #000;
`;

const ButtonWrapper = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  margin-top: 10px;
`;

export { Header, Main, MainFooter, ButtonWrapper };
