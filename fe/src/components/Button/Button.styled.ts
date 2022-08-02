import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

const Wrap = styled.div`
  width: 190px;
  height: 24px;
  border-radius: 10px;
  box-shadow: 10px 5px 5px #bbb;
  ${() => flexLayoutMixin('', 'center', 'center')}
`;

const Text = styled.div`
  margin-left: 10px;
`;

export { Wrap, Text };
