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
  position: relative;
  ${() => flexLayoutMixin('row', 'space-between', 'center')}
  width: 1000px;
  height: 660px;
  border-radius: 10px;
  margin-left: 10px;
`;

export const InputWrap = styled.div`
  display: flex;
  flex-direction: column;
  width: 400px;
  height: 650px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 5px;
  overflow-y: auto;
  overflow-x: hidden;

  ::-webkit-scrollbar {
    width: 0;
  }

  > div:not(:last-child) {
    border-bottom: 1px solid grey;
  }
`;

export const PostBtnWrap = styled.div`
  position: absolute;
  bottom: 50px;
  right: 20px;
`;

export const Img = styled.img`
  object-fit: cover;
`;
