import { MESSAGE } from 'constants/message';
import { ROUTE_PATH } from 'constants/route';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { validateAccessToken } from 'utils/fetch';
import { openErrorToast } from 'utils/toast';
import { accountStore } from '../store/AccountStore';

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
        openErrorToast(MESSAGE.ERROR.EXPIRED_ACCESS_TOKEN);
        navigate(ROUTE_PATH.LOGIN);
      }
    }
  }, [navigate]);
}

export default useAuth;
