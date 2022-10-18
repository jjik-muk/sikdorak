import { LOGO } from 'styles/size';
import { Link } from 'react-router-dom';
import LogoIcon from '../../../assets/Logo.png';

function Logo() {
  return (
    <Link to="/">
      <img src={LogoIcon} alt="logo" width={LOGO.WIDTH} />
    </Link>
  );
}

export default Logo;
