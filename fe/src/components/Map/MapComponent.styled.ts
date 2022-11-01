import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { COLOR } from 'styles/color';

export const KakaoMap = styled.div`
  width: 100%;
  height: 100%;

  .custom-overlay {
    :after {
      border-top: 10px solid ${COLOR.BLUE};
      border-left: 10px solid transparent;
      border-right: 10px solid transparent;
      border-bottom: 0px solid transparent;
      content: '';
      position: absolute;
      top: 38px;
      left: 47%;
    }
  }
`;

export const overlayStyle = `
  position: relative;
  padding: 10px;
  height: 40px;
  font-weight: 700;
  font-size: 18px;
  background-color: ${COLOR.BLUE};
  color: ${COLOR.WHITE};
  cursor: pointer;
  transition: transform 0.1s ease 0s;
  ${STYLE.BOX_CONTAINER}
`;
