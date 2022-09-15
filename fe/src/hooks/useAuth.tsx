import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';

function useAuth() {
  const navigate = useNavigate();

  useEffect(() => {
    moveToLoginPageIfExpiredAccessToken();

    async function moveToLoginPageIfExpiredAccessToken() {
      const isExpiredAccessToken = await validateAccessToken();
      if (isExpiredAccessToken) {
        navigate('/login');
      }
    }

    async function validateAccessToken() {
      const res = await fetchDataThatNeedToLogin('api/users/me');
      return res.code === 'F-O003' || res.code === 'F-O004';
    }
  }, []);
}

export default useAuth;
