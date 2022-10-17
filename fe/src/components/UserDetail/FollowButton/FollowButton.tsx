import Button from '@mui/material/Button';
import { observer } from 'mobx-react';
import { useLocation } from 'react-router-dom';
import { userStore } from 'stores/userStore';

const FollowButton = observer(() => {
  const { pathname } = useLocation();
  const IDtoFollow = Number(pathname.split('/').at(-1));

  return userStore?.followStatus ? (
    <Button variant="contained" onClick={() => userStore.postUnFollow(IDtoFollow)}>
      언팔로우
    </Button>
  ) : (
    <Button variant="contained" onClick={() => userStore.postFollow(IDtoFollow)}>
      팔로우
    </Button>
  );
});

export default FollowButton;
