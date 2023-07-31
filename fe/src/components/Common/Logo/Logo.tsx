import { LOGO } from 'styles/size';
import { Link } from 'react-router-dom';
import LogoIcon from '../../../assets/Logo.png';
import { ALT } from 'constants/alt';

function Logo() {
  return (
    <Link to="/">
      <img src={LogoIcon} alt={ALT.LOGO} width={LOGO.WIDTH} />
    </Link>
  );
}

export default Logo;
