import { STYLE } from 'constants/style';
import styled from 'styled-components';

export const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  ${STYLE.BOX_CONTAINER}
  padding: 10px;
`;
