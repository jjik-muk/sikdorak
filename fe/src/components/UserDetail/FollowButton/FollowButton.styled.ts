import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { COLOR } from 'styles/color';

export const Wrap = styled.button`
  width: 120px;
  background-color: blue;
  color: ${COLOR.WHITE};
  text-align: center;
  padding: 10px;
  ${STYLE.BOX_CONTAINER}
`;
