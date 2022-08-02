import SIZE from 'constants/size';
import { ReactComponent as DownArrow } from 'assets/DownArrow.svg';
import { ReactComponent as Photo } from 'assets/Photo.svg';
import { ReactComponent as Star } from 'assets/Star.svg';
import { ReactComponent as UpArrow } from 'assets/UpArrow.svg';

const iconComponents = {
  Photo,
  DownArrow,
  UpArrow,
  Star,
};

function Icon({
  icon,
  width = SIZE.ICON.WIDTH,
  height = SIZE.ICON.HEIGHT,
  fill = 'none',
  stroke = '#000',
  onClick,
}: IconProps) {
  const SelectedIcon = iconComponents[icon];

  if (!SelectedIcon) {
    throw new Error(`${icon} 컴포넌트를 찾을 수 없습니다. `);
  }

  return <SelectedIcon width={width} height={height} fill={fill} stroke={stroke} onClick={onClick} />;
}

export default Icon;

type IconComponentsKeys = keyof typeof iconComponents;

interface IconProps {
  icon: IconComponentsKeys;
  width?: number;
  height?: number;
  fill?: string;
  stroke?: string;
  onClick?: (idx: number) => void;
}
