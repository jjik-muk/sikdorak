import Button from '@mui/material/Button';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';

function FollowButton({ alreadyFollowed }: FollowButtonProps) {
  const [isFriendship, setIsFriendship] = useState(alreadyFollowed);
  const { pathname } = useLocation();
  const IDtoFollow = Number(pathname.split('/').at(-1));

  useEffect(() => {
    setIsFriendship(alreadyFollowed);
  }, [alreadyFollowed]);

  return isFriendship ? (
    <Button variant="contained" onClick={postUnFollow}>
      언팔로우
    </Button>
  ) : (
    <Button variant="contained" onClick={postFollow}>
      팔로우
    </Button>
  );

  function postFollow() {
    fetchDataThatNeedToLogin(`api/users/follow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    setIsFriendship(!isFriendship);
    // TODO: UserStore.fetchUser();
  }
  function postUnFollow() {
    fetchDataThatNeedToLogin(`api/users/unfollow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    setIsFriendship(!isFriendship);
    // TODO: UserStore.fetchUser();
  }
}

type FollowButtonProps = {
  alreadyFollowed?: boolean;
};

export default FollowButton;
