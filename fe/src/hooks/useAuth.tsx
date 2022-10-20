import { MESSAGE } from 'constants/message';
import { ROUTE_PATH } from 'constants/routePath';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { validateAccessToken } from 'utils/fetch';
import { accountStore } from '../stores/AccountStore';

function useAuth() {
  const navigate = useNavigate();

  useEffect(() => {
    moveToLoginPageIfNotValidateAccessToken();

    async function moveToLoginPageIfNotValidateAccessToken() {
      const isLogin = accountStore.id;
      if (!isLogin) {
        return;
      }
      if (!validateAccessToken()) {
        alert(MESSAGE.ERROR.EXPIRED_ACCESS_TOKEN);
        navigate(ROUTE_PATH.LOGIN);
      }
    }
  }, [navigate]);
}

export default useAuth;
