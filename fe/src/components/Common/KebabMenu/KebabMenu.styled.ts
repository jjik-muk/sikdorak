import styled from 'styled-components';
import { COLOR } from 'styles/color';

export const Wrap = styled.div`
  position: absolute;
  width: 100px;
  height: fit-content;
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  background-color: ${COLOR.WHITE};
  border-radius: 5px;

  > div:not(:last-child) {
    border-bottom: 1px solid grey;
  }
`;

export const Btn = styled.div`
  padding: 10px;
  text-align: center;
  cursor: pointer;
`;

export const DeleteBtn = styled(Btn)`
  color: red;
`;
