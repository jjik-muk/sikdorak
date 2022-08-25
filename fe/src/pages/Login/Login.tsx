import { DOMAIN } from 'constants/dummyData';
import TEXT from 'constants/text';
import Logo from 'common/Logo/Logo';
import Portal from 'common/Portal/Portal';
import CommonHeader from 'components/Common/CommonHeader';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useToggle from 'hooks/useToggle';
import ReviewDetail from 'pages/ReviewDetail/ReviewDetail';
import { useRef } from 'react';
import { Navigate } from 'react-router-dom';
import { Form, KakaoLogin, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  const [isPortal, toggleIsPortal] = useToggle(false);
  const modalRef = useRef(null);
  useOutsideClick(modalRef, toggleIsPortal);

  const accessToken = localStorage.getItem('accessToken');
  const hasAccessToken = accessToken && accessToken !== 'null';

  if (hasAccessToken) {
    return <Navigate to="/reviewList" />;
  }

  return (
    <>
      <CommonHeader />
      <Wrap>
        <Form>
          <Logo />
          <KakaoLogin href={`${DOMAIN}/api/oauth/login`}>
            <LoginButton text={KAKAO} />
          </KakaoLogin>
          <LoginInput type={ID} />
          <LoginInput type={PASSWORD} />
          <LoginButton text={NORMAL} onClick={toggleIsPortal} />
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
