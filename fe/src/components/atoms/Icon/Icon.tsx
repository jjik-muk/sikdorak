import CreateIcon from '@mui/icons-material/Create';
import CloseIcon from '@mui/icons-material/Close';
import CancelIcon from '@mui/icons-material/Cancel';
import DeleteIcon from '@mui/icons-material/Delete';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import LibraryAddIcon from '@mui/icons-material/LibraryAdd';
import MapIcon from '@mui/icons-material/Map';
import ModeCommentIcon from '@mui/icons-material/ModeComment';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import KeyboardArrowRightOutlinedIcon from '@mui/icons-material/KeyboardArrowRightOutlined';
import KeyboardArrowLeftOutlinedIcon from '@mui/icons-material/KeyboardArrowLeftOutlined';
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu';
import ShareIcon from '@mui/icons-material/Share';
import StarRateIcon from '@mui/icons-material/StarRate';

export const iconComponents = {
  CreateIcon,
  CloseIcon,
  CancelIcon,
  DeleteIcon,
  ExpandMoreIcon,
  FavoriteIcon,
  HomeIcon,
  LibraryAddIcon,
  MapIcon,
  ModeCommentIcon,
  MoreVertIcon,
  KeyboardArrowRightOutlinedIcon,
  KeyboardArrowLeftOutlinedIcon,
  RestaurantMenuIcon,
  ShareIcon,
  StarRateIcon,
};

function Icon({ type, ...rest }: IconProps) {
  const SelectedIcon = iconComponents[type];
  if (!SelectedIcon) {
    throw new Error(`${type} 컴포넌트를 찾을 수 없습니다. `);
  }
  return <SelectedIcon {...rest} />;
}

export default Icon;

export type IconComponentsKeys = keyof typeof iconComponents;

type IconProps = {
  type: IconComponentsKeys;
  color?: 'inherit' | 'action' | 'disabled' | 'primary' | 'secondary' | 'error' | 'info' | 'success' | 'warning';
};
