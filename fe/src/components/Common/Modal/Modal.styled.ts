import { STYLE } from 'constants/style';
import { Z_INDEX } from 'constants/zIndex';
import styled from 'styled-components';

type ModalWrapperProps = {
  width?: string;
  height?: string;
};

export const ModalWrapper = styled.div<ModalWrapperProps>`
  position: absolute;
  z-index: ${Z_INDEX.MODAL.CONTENTS};
  background-color: #fff;
  border: 1px solid #000;
  ${({ width }) => `width:${width}`};
  ${({ height }) => `height:${height}`};
  overflow: auto;
  ${STYLE.BOX_CONTAINER}
`;
