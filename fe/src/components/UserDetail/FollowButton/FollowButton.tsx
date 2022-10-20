import Button from '@mui/material/Button';
import { observer } from 'mobx-react';
import { useLocation } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import { userStore } from 'stores/userStore';
import { openWarningToast } from 'utils/toast';

const FollowButton = observer(() => {
  const { pathname } = useLocation();
  const IDtoFollow = Number(pathname.split('/').at(-1));

  const handleClickUnFollow = () => {
    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }
    userStore.postUnFollow(IDtoFollow);
  };

  const handleClickFollow = () => {
    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }
    userStore.postFollow(IDtoFollow);
  };

  return userStore?.followStatus ? (
    <Button variant="contained" onClick={handleClickUnFollow}>
      언팔로우
    </Button>
  ) : (
    <Button variant="contained" onClick={handleClickFollow}>
      팔로우
    </Button>
  );
});

export default FollowButton;
