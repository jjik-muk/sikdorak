import Icon from 'common/Icon';
import { ReactNode } from 'react';
import { Wrap } from './IconButton.styled';

function IconButton({ children, icon, width, height, fill, stroke }: IconButtonProps) {
  return (
    <Wrap width={width} height={height}>
      <Icon icon={icon} fill={fill} stroke={stroke} />
      {children}
    </Wrap>
  );
}

export default IconButton;

type IconTypes = 'Photo' | 'DownArrow' | 'UpArrow' | 'Star' | 'MenuBtn' | 'Heart' | 'ShareArrow' | 'TalkBubble';

type IconButtonProps = {
  icon: IconTypes;
  children?: ReactNode;
  width: number;
  height: number;
  fill?: string;
  stroke?: string;
};
