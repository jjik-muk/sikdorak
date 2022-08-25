import { DOMAIN } from 'constants/dummyData';
import TEXT from 'constants/text';
import Logo from 'common/Logo/Logo';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import { Navigate } from 'react-router-dom';
import { Form, KakaoLogin, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  const accessToken = localStorage.getItem('accessToken');
  const hasAccessToken = accessToken && accessToken !== 'null';

  if (hasAccessToken) {
    return <Navigate to="/reviewList" />;
  }

  return (
    <Wrap>
      <Form>
        <Logo />
        <KakaoLogin href={`${DOMAIN}/api/oauth/login`}>
          <LoginButton text={KAKAO} />
        </KakaoLogin>
        <LoginInput type={ID} />
        <LoginInput type={PASSWORD} />
        <LoginButton text={NORMAL} />
        <span>회원가입</span>
      </Form>
    </Wrap>
  );
}

export default Login;
