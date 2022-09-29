import Loading from 'components/Common/Loading/Loading';
import { observer } from 'mobx-react';
import { useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';

const Callback = observer(() => {
  const [searchParams] = useSearchParams();
  const kakaoAuthorizationCode = searchParams.get('code');

  useEffect(() => {
    accountStore.setAccessToken(kakaoAuthorizationCode);
  }, []);

  if (!accountStore.accessToken) {
    return <Loading />;
  }

  return <Navigate to="/" />;
});

export default Callback;
