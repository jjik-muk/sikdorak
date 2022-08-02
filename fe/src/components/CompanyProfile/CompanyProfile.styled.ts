import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

const Wrap = styled.div`
  ${() => flexLayoutMixin('', '', 'center')}
  display: flex;
  gap: 10px;
  padding: 20px;
`;

// TODO: width, height 매직 넘버 추출
const Picture = styled.img`
  width: 42px;
  height: 42px;
`;

const CompanyName = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;

const Region = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;

export { Wrap, Picture, CompanyName, Region };
