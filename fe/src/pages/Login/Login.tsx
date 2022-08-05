import TEXT from 'constants/text';
import Portal from 'common/Portal/Portal';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import Logo from 'components/Logo/Logo';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useState } from 'react';
import { Form, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  const [isPortal, setIsPortal] = useState(false);

  const handleClick = () => {
    setIsPortal(!isPortal);
  };

  return (
    <Wrap>
      <Form>
        <Logo />
        <LoginButton text={KAKAO} />
        <LoginInput type={ID} />
        <LoginInput type={PASSWORD} />
        <LoginButton text={NORMAL} onClick={handleClick} />
        <span>회원가입</span>
        {isPortal && (
          <Portal selector="#portal">
            <ReviewDetail />
          </Portal>
        )}
      </Form>
    </Wrap>
  );
}

export default Login;
