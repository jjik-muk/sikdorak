import { Link } from 'react-router-dom';
import LogoIcon from '../../../assets/Logo.png';

function Logo() {
  return (
    <Link to="/">
      <img src={LogoIcon} alt="logo" width={100} />
    </Link>
  );
}

export default Logo;
