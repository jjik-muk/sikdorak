import { createPortal } from 'react-dom';

export default function Portal({ children, selector }: PortalProps) {
  const element = typeof window !== 'undefined' && document.querySelector(selector);
  return element && children ? createPortal(children, element) : null;
}

interface PortalProps {
  children: React.ReactNode;
  selector: string;
}
