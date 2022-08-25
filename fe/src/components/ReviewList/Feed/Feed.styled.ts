import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  display: flex;
  width: fit-content;
  ${STYLE.BOX_CONTAINER}
  margin-bottom: 24px;
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

export const ContentsWrap = styled.div<{ wrapWidth: number }>`
  width: ${({ wrapWidth }) => wrapWidth}px;
  padding: 20px;
  max-height: 600px;
  overflow-y: auto;
`;

export const Contents = styled.p`
  padding: 20px;
  margin-bottom: 10px;
  max-height: 69px;

  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

export const IconWrap = styled.div<{ width: number; height: number }>`
  width: ${({ width }) => width}px;
  height: ${({ height }) => height}px;
  ${STYLE.BOX_CONTAINER}
  ${() => flexLayoutMixin('', 'center', 'center')}
`;

export const Pictures = styled.div`
  padding: 0 20px 0 20px;
`;
