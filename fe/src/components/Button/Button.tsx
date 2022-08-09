import Icon from 'common/Icon';
import { Text, Wrap } from './Button.styled';

function Button({ text, icon }: ButtonProps) {
  return (
    <Wrap>
      <Icon icon={icon} />
      <Text>{text}</Text>
    </Wrap>
  );
}

export default Button;

type IconTypes = 'Photo' | 'DownArrow' | 'UpArrow' | 'Star' | 'MenuBtn' | 'Heart' | 'ShareArrow' | 'TalkBubble';

interface ButtonProps {
  icon: IconTypes;
  text?: string;
}
