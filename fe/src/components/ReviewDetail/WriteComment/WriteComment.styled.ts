import { STYLE } from 'constants/style';
import styled from 'styled-components';

export const Input = styled.textarea`
  margin-top: 10px;
  width: 100%;
  height: 30px;
  padding: 7px;
  ${STYLE.BOX_CONTAINER}
  resize: none;
  overflow: hidden;
`;
