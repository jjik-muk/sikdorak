import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { accountStore } from '../stores/AccountStore';

function useAuth() {
  const navigate = useNavigate();

  useEffect(() => {
    moveToLoginPageIfExpiredAccessToken();

    async function moveToLoginPageIfExpiredAccessToken() {
      if (!accountStore.id) {
        return;
      }
      const isExpiredAccessToken = await validateAccessToken();
      if (isExpiredAccessToken) {
        console.log('?!!!');
        navigate('/login');
      }
    }

    async function validateAccessToken() {
      const res = await fetchDataThatNeedToLogin('api/users/me');
      return res.code === 'F-O003' || res.code === 'F-O004';
    }
  }, [navigate]);
}

export default useAuth;
