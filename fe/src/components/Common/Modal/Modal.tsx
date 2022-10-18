import { MODAL } from 'constants/size';
import { ModalWrapper } from './Modal.styled';

export default function Modal({ children, width, height = MODAL.DEFAULT.HEIGHT, fullWidth }: ModalProps) {
  return (
    <ModalWrapper width={width} height={height} fullWidth={fullWidth}>
      {children}
    </ModalWrapper>
  );
}

type ModalProps = {
  children: React.ReactNode;
  width?: number;
  height?: number;
  fullWidth?: boolean;
};
