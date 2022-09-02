import { DOMAIN } from 'constants/dummyData';
import { STATUS_CODE } from 'constants/statusCode';
import Loading from 'components/Common/Loading/Loading';
import { Dispatch, useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';

function Callback({ accessToken, setAccessToken }: CallbackProps) {
  const [searchParams] = useSearchParams();
  const kakaoAuthorizationCode = searchParams.get('code');

  useEffect(() => {
    fetchAccessToken();

    async function fetchAccessToken() {
      const res = await fetch(`${DOMAIN}/api/oauth/callback?code=${kakaoAuthorizationCode}`, {
        credentials: 'include',
      });
      const resJson = await res.json();
      const { code, data } = resJson;

      if (code === STATUS_CODE.FAIL_COMMUNICATION_WITH_OAUTH_SERVER) {
        // TODO: 내부 에러 처리 시도 및 유저에게 알려주기
        throw new Error('OAuth 서버와의 통신이 원할하지 않습니다.');
      }
      setAccessToken(data.accessToken);
      localStorage.setItem('accessToken', data.accessToken);
    }
  }, []);

  if (!accessToken) {
    return <Loading />;
  }

  return <Navigate to="/" />;
}

type CallbackProps = {
  accessToken: string;
  setAccessToken: Dispatch<any>;
};

export default Callback;
