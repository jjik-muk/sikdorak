import { TEXT } from 'constants/text';
import Logo from 'components/Common/Logo/Logo';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import { Form, KakaoLogin, Wrap } from './Login.styled';

const { KAKAO } = TEXT.LOGIN_BTN;

function Login() {
  return (
    <Wrap>
      <Form>
        <Logo />
        <div>광고 리뷰는 싫어! 미식가들의 솔직 담백한 음식 리뷰 SNS! 🍱 식도락입니다.</div>
        <KakaoLogin href={`${process.env.REACT_APP_BE_SERVER_URL}/api/oauth/login`}>
          <LoginButton text={KAKAO} />
        </KakaoLogin>
      </Form>
    </Wrap>
  );
}

export default Login;
