import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Modal = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')}
  position: fixed;
  left: 50%;
  top: 10%;
`;

export const Background = styled.div`
  height: 800px;
  width: 800px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  overflow: auto;
`;
