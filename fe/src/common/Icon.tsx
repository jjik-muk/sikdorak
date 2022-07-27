import SIZE from 'constants/size';
import { ReactComponent as Photo } from 'assets/Photo.svg';

const iconComponents = {
  Photo,
};

function Icon({ icon, width = SIZE.ICON.WIDTH, height = SIZE.ICON.HEIGHT, fill = 'none', stroke = '#000' }: IconProps) {
  const SelectedIcon = iconComponents[icon];

  if (!SelectedIcon) {
    throw new Error(`${icon} 컴포넌트를 찾을 수 없습니다. `);
  }

  return <SelectedIcon width={width} height={height} fill={fill} stroke={stroke} />;
}

export default Icon;

type IconComponentsKeys = keyof typeof iconComponents;

interface IconProps {
  icon: IconComponentsKeys;
  width?: number;
  height?: number;
  fill?: string;
  stroke?: string;
}
