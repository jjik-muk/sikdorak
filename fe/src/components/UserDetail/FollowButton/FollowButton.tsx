import Button from '@mui/material/Button';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation } from 'react-router-dom';
import { ThunkDispatch } from 'redux-thunk';
import { RootState } from 'store/modules/store';
import { UserAction, postFollow, postUnfollow } from 'store/modules/user';
import { openWarningToast } from 'utils/toast';

function FollowButton() {
  const { pathname } = useLocation();
  const targetID = Number(pathname.split('/').at(-1));
  const userStore = useSelector((state: RootState) => state.user);
  const accountStore = useSelector((state: RootState) => state.account);
  const dispatch: ThunkDispatch<RootState, null, UserAction> = useDispatch();

  const handleClickUnFollow = () => {
    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }
    dispatch(postUnfollow(targetID));
  };

  const handleClickFollow = () => {
    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }
    dispatch(postFollow(targetID));
  };

  return userStore?.relationStatus.followStatus ? (
    <Button variant="contained" onClick={handleClickUnFollow}>
      언팔로우
    </Button>
  ) : (
    <Button variant="contained" onClick={handleClickFollow} role="button">
      팔로우
    </Button>
  );
}

export default FollowButton;
