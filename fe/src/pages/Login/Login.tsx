import TEXT from 'constants/text';
import Logo from 'components/Common/Logo/Logo';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import { Form, KakaoLogin, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  return (
    <Wrap>
      <Form>
        <Logo />
        <KakaoLogin href={`${process.env.REACT_APP_BE_SERVER_URL}/api/oauth/login`}>
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
