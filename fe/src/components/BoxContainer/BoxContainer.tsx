import { ReactNode } from 'react';
import { Wrap } from './BoxContainer.styled';

function BoxContainer({ children }: BoxContainerProps) {
  return <Wrap>{children}</Wrap>;
}

export default BoxContainer;

interface BoxContainerProps {
  children: ReactNode;
}
