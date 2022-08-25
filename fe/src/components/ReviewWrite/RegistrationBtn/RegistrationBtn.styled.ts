import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 100px;
  height: 27px;
  padding: 20px 20px;
  margin: 30px 280px;
  ${STYLE.BOX_CONTAINER}
  ${() => flexLayoutMixin('', 'center', 'center')}
  cursor: pointer;
`;
