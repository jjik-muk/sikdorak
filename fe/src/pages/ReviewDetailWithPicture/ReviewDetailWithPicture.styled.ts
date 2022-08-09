import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  display: flex;
`;

export const Header = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')}
`;

export const Main = styled.div`
  border-radius: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

export const MainFooter = styled.div`
  border-top: 1px solid #000;
`;

export const ButtonWrapper = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  margin: 30px 0;
`;

export const ContentsWrap = styled.div`
  width: 400px;
  padding: 20px;
  max-height: 600px;
  overflow-y: auto;
`;
