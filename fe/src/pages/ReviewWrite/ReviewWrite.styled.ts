import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Title = styled.div`
  ${() => flexLayoutMixin('row', 'center', 'center')};
`;

export const Wrap = styled.div`
  ${() => flexLayoutMixin('row', 'space-between', 'center')}
  height: 100vh;
`;
