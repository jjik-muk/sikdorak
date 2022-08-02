import styled from 'styled-components';

interface ModalWrapperProps {
  width?: string;
  height?: string;
}

export const ModalWrapper = styled.div<ModalWrapperProps>`
  background-color: #fff;
  border: 1px solid #000;
  padding: 15px 10px;
  ${({ width }) => `width:${width}`};
  ${({ height }) => `height:${height}`};
  overflow: auto;
`;
