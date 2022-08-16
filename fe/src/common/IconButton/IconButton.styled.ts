import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div<{ width: number; height: number }>`
  width: ${({ width }) => width}px; // 24
  height: ${({ height }) => height}px; // 24
  border-radius: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  ${() => flexLayoutMixin('', 'center', 'center')}
`;

export const Text = styled.div`
  margin-left: 10px;
`;
