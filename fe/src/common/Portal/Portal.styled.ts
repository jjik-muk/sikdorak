import { STYLE } from 'constants/style';
import { Z_INDEX } from 'constants/zIndex';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Modal = styled.div`
  position: fixed;
  ${() => flexLayoutMixin('', 'center', 'center')}
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`;

export const Background = styled.div`
  position: relative;
  width: fit-content;
  max-height: 800px;
  background-color: #fff;
  ${STYLE.BOX_CONTAINER}
  z-index: ${Z_INDEX.MODAL.CONTENTS};
`;

export const Dim = styled.div`
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: grey;
  opacity: 0.5;
  z-index: ${Z_INDEX.MODAL.DIM};
`;
