import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Wrap } from './FollowButton.styled';

function FollowButton({ alreadyFollowed }: FollowButtonProps) {
  const [isFriendship, setIsFriendship] = useState(alreadyFollowed);
  const { pathname } = useLocation();
  const IDtoFollow = Number(pathname.split('/').at(-1));

  useEffect(() => {
    setIsFriendship(alreadyFollowed);
  }, [alreadyFollowed]);

  return isFriendship ? <Wrap onClick={postUnFollow}>언팔로우</Wrap> : <Wrap onClick={postFollow}>팔로우</Wrap>;

  function postFollow() {
    fetchDataThatNeedToLogin(`api/users/follow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    setIsFriendship(!isFriendship);
  }
  function postUnFollow() {
    fetchDataThatNeedToLogin(`api/users/unfollow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    setIsFriendship(!isFriendship);
  }
}

type FollowButtonProps = {
  alreadyFollowed?: boolean;
};

export default FollowButton;
