import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  display: flex;
`;

export const Header = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')}
`;

export const Main = styled.div`
  ${STYLE.BOX_CONTAINER}
`;

export const MainFooter = styled.div`
  border-top: 1px solid #000;
`;

export const ButtonWrapper = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  margin: 30px 0;
`;

export const IconWrap = styled.div<{ width: number; height: number }>`
  width: ${({ width }) => width}px; // 24
  height: ${({ height }) => height}px; // 24
  ${STYLE.BOX_CONTAINER}
  ${() => flexLayoutMixin('', 'center', 'center')}
`;

export const ContentsWrap = styled.div<{ wrapWidth: number }>`
  width: ${({ wrapWidth }) => wrapWidth}px;
  max-height: 600px;
  overflow-y: auto;
`;

export const Contents = styled.div`
  padding: 20px;
`;
