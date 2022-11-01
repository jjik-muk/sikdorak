import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { COLOR } from 'styles/color';

export const MapArea = styled.div`
  flex: 5;
  height: 1024px;
  background-color: green;
  z-index: 0;
  position: relative;
`;

export const ContentArea = styled.div`
  min-width: 300px;
  display: flex;
`;

export const FeedsArea = styled.div`
  flex: 2;
  padding: 20px;
  overflow-x: hidden;
  overflow-y: auto;
  height: 910px;
  display: flex;
  flex-direction: column;
`;

export const Buttons = styled.div`
  display: flex;
  flex-direction: column;

  > button {
    width: 60px;
    height: 60px;
    background-color: ${COLOR.WHITE};
    ${STYLE.BOX_CONTAINER}
  }
`;

export const Input = styled.input`
  width: 100%;
  height: 50px;
`;
