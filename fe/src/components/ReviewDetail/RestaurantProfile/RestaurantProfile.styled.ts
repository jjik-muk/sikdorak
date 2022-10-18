import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('', '', 'center')}
  display: flex;
  gap: 10px;
  padding: 20px;
  width: fit-content;
  cursor: pointer;
`;

export const CompanyName = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;

export const Region = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;
