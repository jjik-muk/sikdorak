import { ModalWrapper } from './Modal.styled';

interface ModalProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
}

export default function Modal({ children, width = '100%', height = '200px' }: ModalProps) {
  return (
    <ModalWrapper width={width} height={height}>
      {children}
    </ModalWrapper>
  );
}
