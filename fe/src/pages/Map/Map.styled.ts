import { STYLE } from 'constants/style';
import styled from 'styled-components';

export const MapArea = styled.div`
  flex: 5;
  height: 1024px;
  background-color: green;
  z-index: 0;
`;

export const ContentArea = styled.div`
  min-width: 300px;
  display: flex;
`;

export const FeedsArea = styled.div`
  flex: 2;
  border: 1px solid black;
  padding: 20px;
  overflow-y: auto;
  height: 910px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const Buttons = styled.div`
  display: flex;
  flex-direction: column;

  > button {
    width: 60px;
    height: 60px;
    background-color: #fff;
    ${STYLE.BOX_CONTAINER}
  }
`;

export const Input = styled.input`
  width: 100%;
  height: 50px;
`;
