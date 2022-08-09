import Icon from 'common/Icon';
import { ReactNode } from 'react';
import { Wrap } from './Button.styled';

function Button({ children, icon, width, height }: ButtonProps) {
  return (
    <Wrap width={width} height={height}>
      <Icon icon={icon} />
      {children}
    </Wrap>
  );
}

export default Button;

type IconTypes = 'Photo' | 'DownArrow' | 'UpArrow' | 'Star' | 'MenuBtn' | 'Heart' | 'ShareArrow' | 'TalkBubble';

interface ButtonProps {
  icon: IconTypes;
  children?: ReactNode;
  width: number;
  height: number;
}
