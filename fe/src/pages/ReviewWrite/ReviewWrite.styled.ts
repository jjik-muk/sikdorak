import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  display: flex;
  width: 1400px;
  flex-direction: column;
  padding: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 7px;
`;

export const Content = styled.div`
  display: flex;
`;

export const Title = styled.div`
  ${() => flexLayoutMixin('row', 'center', 'center')};
  font-size: 20px;
  font-weight: 700;
`;

export const ImgWrap = styled.div`
  ${() => flexLayoutMixin('row', 'space-between', 'center')}
  width: 1000px;
  height: 660px;
`;

export const InputWrap = styled.div`
  display: flex;
  flex-direction: column;
  height: 650px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 5px;
  overflow-y: auto;

  > div:not(:last-child) {
    border-bottom: 1px solid grey;
  }
`;
