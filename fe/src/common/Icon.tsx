import { ICON } from 'constants/size';
import { ReactComponent as Alarm } from 'assets/Alarm.svg';
import { ReactComponent as DownArrow } from 'assets/DownArrow.svg';
import { ReactComponent as Heart } from 'assets/Heart.svg';
import { ReactComponent as Home } from 'assets/Home.svg';
import { ReactComponent as Location } from 'assets/Location.svg';
import { ReactComponent as Map } from 'assets/Map.svg';
import { ReactComponent as MenuBtn } from 'assets/MenuBtn.svg';
import { ReactComponent as Phone } from 'assets/Phone.svg';
import { ReactComponent as Photo } from 'assets/Photo.svg';
import { ReactComponent as PostBtn } from 'assets/PostBtn.svg';
import { ReactComponent as Profile } from 'assets/Profile.svg';
import { ReactComponent as SearchBtn } from 'assets/SearchBtn.svg';
import { ReactComponent as ShareArrow } from 'assets/ShareArrow.svg';
import { ReactComponent as Star } from 'assets/Star.svg';
import { ReactComponent as TalkBubble } from 'assets/TalkBubble.svg';
import { ReactComponent as UpArrow } from 'assets/UpArrow.svg';

export const iconComponents = {
  Photo,
  DownArrow,
  UpArrow,
  Star,
  MenuBtn,
  Heart,
  ShareArrow,
  TalkBubble,
  Alarm,
  Home,
  Map,
  PostBtn,
  SearchBtn,
  Profile,
  Location,
  Phone,
};

function Icon({ icon, width = ICON.WIDTH, height = ICON.HEIGHT, fill = 'none', stroke = '#000', onClick }: IconProps) {
  const SelectedIcon = iconComponents[icon];

  if (!SelectedIcon) {
    throw new Error(`${icon} 컴포넌트를 찾을 수 없습니다. `);
  }

  return <SelectedIcon width={width} height={height} fill={fill} stroke={stroke} onClick={onClick} />;
}

export default Icon;

export type IconComponentsKeys = keyof typeof iconComponents;

type IconProps = {
  icon: IconComponentsKeys;
  width?: number;
  height?: number;
  fill?: string;
  stroke?: string;
  onClick?: (idx: number) => void;
};
