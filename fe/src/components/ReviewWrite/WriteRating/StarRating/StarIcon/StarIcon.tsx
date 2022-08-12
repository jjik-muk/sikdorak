import Icon from 'common/Icon';
import { Wrap } from './StarIcon.styled';

function StarIcon({ onClick, fill }: StarIconProps) {
  const isFilledColor = fill !== 'none';
  return (
    <Wrap onClick={onClick}>
      <Icon icon="Star" fill={fill} stroke={isFilledColor ? fill : 'black'} />
    </Wrap>
  );
}

export default StarIcon;

interface StarIconProps {
  onClick: () => void;
  fill: string;
}
