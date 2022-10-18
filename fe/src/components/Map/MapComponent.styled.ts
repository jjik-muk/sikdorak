import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { COLOR } from 'styles/color';

export const KakaoMap = styled.div`
  width: 100%;
  height: 100%;

  .custom-overay {
    :after {
      border-top: 10px solid black;
      border-left: 10px solid transparent;
      border-right: 10px solid transparent;
      border-bottom: 0px solid transparent;
      content: '';
      position: absolute;
      top: 38px;
      left: 50%;
    }
  }
`;

export const overlayStyle = `
  position: relative;
  padding: 10px;
  height: 40px;
  background-color: ${COLOR.WHITE};
  border-radius: 50%;
  font-weight: 700;
  font-size: 20px;
  background-color: black;
  color: ${COLOR.WHITE};
  ${STYLE.BOX_CONTAINER}
`;
