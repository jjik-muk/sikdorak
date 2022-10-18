import { STYLE } from 'styles/style';
import { Z_INDEX } from 'styles/zIndex';
import styled from 'styled-components';
import { COLOR } from 'styles/color';

type ModalWrapperProps = {
  width?: number;
  height?: number;
  fullWidth: boolean;
};

export const ModalWrapper = styled.div<ModalWrapperProps>`
  position: absolute;
  z-index: ${Z_INDEX.MODAL.CONTENTS};
  background-color: ${COLOR.WHITE};
  border: 1px solid ${COLOR.BLACK};
  ${({ width, fullWidth }) => (fullWidth ? 'width: 100%' : `width: ${width}px`)};
  ${({ height }) => `height: ${height}px`};
  overflow: auto;
  ${STYLE.BOX_CONTAINER}
`;
