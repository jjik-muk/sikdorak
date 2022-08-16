import TEXT from 'constants/text';
import Logo from 'common/Logo/Logo';
import Portal from 'common/Portal/Portal';
import CommonHeader from 'components/Common/CommonHeader';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import { useOutsideClick } from 'hooks/useOutsideClick';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef, useState } from 'react';
import { Form, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  const [isPortal, setIsPortal] = useState(false);
  const modalRef = useRef(null);

  const handleClick = () => {
    setIsPortal(!isPortal);
  };

  useOutsideClick(modalRef, handleClick);

  return (
    <>
      <CommonHeader />
      <Wrap>
        <Form>
          <Logo />
          <LoginButton text={KAKAO} />
          <LoginInput type={ID} />
          <LoginInput type={PASSWORD} />
          <LoginButton text={NORMAL} onClick={handleClick} />
          <span>회원가입</span>
          {isPortal && (
            <Portal selector="#portal" ref={modalRef}>
              <ReviewDetail />
            </Portal>
          )}
        </Form>
      </Wrap>
    </>
  );
}

export default Login;
