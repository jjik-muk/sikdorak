import Loading from 'components/Common/Loading/Loading';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Navigate, useSearchParams } from 'react-router-dom';
import { ThunkDispatch } from 'redux-thunk';
import { AccountAction, fetchAccessToken } from 'store/modules/account';
import { RootState } from 'store/modules/store';

function KakaoOAuthCallback() {
  const [searchParams] = useSearchParams();
  const kakaoAuthorizationCode = searchParams.get('code');
  const dispatch: ThunkDispatch<RootState, null, AccountAction> = useDispatch();
  const accessToken = useSelector((state: any) => state.account.accessToken);

  useEffect(() => {
    dispatch(fetchAccessToken(kakaoAuthorizationCode));
  }, [kakaoAuthorizationCode]);

  if (accessToken) {
    return <Loading />;
  }

  return <Navigate to="/" />;
}

export default KakaoOAuthCallback;
