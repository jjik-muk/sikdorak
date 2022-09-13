import { STYLE } from 'constants/style';
import styled from 'styled-components';

type ModalWrapperProps = {
  width?: string;
  height?: string;
};

export const ModalWrapper = styled.div<ModalWrapperProps>`
  background-color: #fff;
  border: 1px solid #000;
  ${({ width }) => `width:${width}`};
  ${({ height }) => `height:${height}`};
  overflow: auto;
  ${STYLE.BOX_CONTAINER}
`;
