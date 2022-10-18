import { STYLE } from 'styles/style';
import { Z_INDEX } from 'styles/zIndex';
import styled from 'styled-components';

type ModalWrapperProps = {
  width?: number;
  height?: number;
  fullWidth: boolean;
};

export const ModalWrapper = styled.div<ModalWrapperProps>`
  position: absolute;
  z-index: ${Z_INDEX.MODAL.CONTENTS};
  background-color: #fff;
  border: 1px solid #000;
  ${({ width, fullWidth }) => (fullWidth ? 'width: 100%' : `width: ${width}`)};
  ${({ height }) => `height: ${height}`};
  overflow: auto;
  ${STYLE.BOX_CONTAINER}
`;
