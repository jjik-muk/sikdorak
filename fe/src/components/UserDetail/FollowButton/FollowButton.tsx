import useToggle from 'hooks/useToggle';
import { useLocation } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Wrap } from './FollowButton.styled';

function FollowButton({ alreadyFollowed }: FollowButtonProps) {
  const [isFriendship, toggleIsFriendship] = useToggle(alreadyFollowed);
  const { pathname } = useLocation();
  const IDtoFollow = Number(pathname.split('/').at(-1));

  return isFriendship ? <Wrap onClick={postUnFollow}>언팔로우</Wrap> : <Wrap onClick={postFollow}>팔로우</Wrap>;

  function postFollow() {
    fetchDataThatNeedToLogin(`api/users/follow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    toggleIsFriendship();
  }
  function postUnFollow() {
    fetchDataThatNeedToLogin(`api/users/unfollow`, {
      method: 'PUT',
      bodyData: { userId: IDtoFollow },
    });
    toggleIsFriendship();
  }
}

type FollowButtonProps = {
  alreadyFollowed?: boolean;
};

export default FollowButton;
