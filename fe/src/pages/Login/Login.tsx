import { DOMAIN } from 'constants/dummyData';
import TEXT from 'constants/text';
import Logo from 'common/Logo/Logo';
import LoginButton from 'components/Login/LoginButton/LoginButton';
import LoginInput from 'components/Login/LoginInput/LoginInput';
import { useUserInfo } from 'context/userInfoProvider';
import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Form, KakaoLogin, Wrap } from './Login.styled';

const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;
const { ID, PASSWORD } = TEXT.INPUT;

function Login() {
  const [, dispatchUserInfo] = useUserInfo();
  const accessToken = localStorage.getItem('accessToken');
  const hasAccessToken = accessToken && accessToken !== 'null';

  useEffect(() => {
    if (hasAccessToken) {
      setMyInfo();
    }

    async function setMyInfo() {
      const myInfo = await fetchDataThatNeedToLogin(`${DOMAIN}/api/users/me`);
      const { id, nickname, profileImage } = myInfo.data;
      const myInfoJson = JSON.stringify({ userId: id, nickname, profileImageUrl: profileImage });
      localStorage.setItem('MY_INFO', myInfoJson);
      dispatchUserInfo({ type: 'SET_USER', userId: id, nickname, profileImageUrl: profileImage });
    }
  }, []);

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
