import { createPortal } from 'react-dom';
import { Modal, Background } from './Portal.styled';

export default function Portal({ children, selector }: PortalProps) {
  const element = typeof window !== 'undefined' && document.querySelector(selector);
  const ModalBackground = (
    <Modal>
      <Background>{children}</Background>
    </Modal>
  );
  return element && children ? createPortal(ModalBackground, element) : null;
}

interface PortalProps {
  children: React.ReactNode;
  selector: string;
}
