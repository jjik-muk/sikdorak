import { API_PATH } from 'constants/apiPath';
import { MESSAGE } from 'constants/message';
import { ROUTE_PATH } from 'constants/routePath';
import { FAILURE_STATUS_CODES } from 'constants/statusCode';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
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

      async function validateAccessToken() {
        const { code } = await fetchDataThatNeedToLogin(API_PATH.USER.MY_PROFILE);
        const isFailure = FAILURE_STATUS_CODES.includes(code);
        return !isFailure;
      }
    }
  }, [navigate]);
}

export default useAuth;
