import TEXT from 'constants/text';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import Logo from 'components/Logo/Logo';
import { Form, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  return (
    <Wrap>
      <Form>
        <Logo />
        <LoginButton text={KAKAO} />
        <LoginInput type={ID} />
        <LoginInput type={PASSWORD} />
        <LoginButton text={NORMAL} />
        <span>회원가입</span>
      </Form>
    </Wrap>
  );
}

export default Login;
