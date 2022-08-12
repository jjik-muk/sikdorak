import { forwardRef } from 'react';
import { createPortal } from 'react-dom';
import { Modal, Background, Dim } from './Portal.styled';

// export default function Portal({ children, selector }: PortalProps) {
//   const element = typeof window !== 'undefined' && document.querySelector(selector);

//   const ModalBackground = (
//     <Modal>
//       <Dim />
//       <Background>{children}</Background>
//     </Modal>
//   );
//   return element && children ? createPortal(ModalBackground, element) : null;
// }

const Portal = forwardRef(({ children, selector }: PortalProps, ref?: any) => {
  const element = typeof window !== 'undefined' && document.querySelector(selector);

  const ModalBackground = (
    <Modal>
      <Dim />
      <Background ref={ref}>{children}</Background>
    </Modal>
  );
  return element && children ? createPortal(ModalBackground, element) : null;
});

export default Portal;

interface PortalProps {
  children: React.ReactNode;
  selector: string;
}
