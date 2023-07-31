import { MESSAGE } from 'constants/message';
import { ROUTE_PATH } from 'constants/route';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { RootState } from 'store/modules/store';
import { validateAccessToken } from 'utils/fetch';
import { openErrorToast } from 'utils/toast';

function useAuth() {
  const navigate = useNavigate();
  const accountStore = useSelector((state: RootState) => state.account);

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
