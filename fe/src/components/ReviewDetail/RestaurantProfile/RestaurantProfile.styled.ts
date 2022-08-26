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

// TODO: width, height 매직 넘버 추출
export const Picture = styled.img`
  width: 42px;
  height: 42px;
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
