import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feeds from 'components/Common/Feeds/Feeds';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { accountStore } from 'store/AccountStore';
import { ActivityInfoWrap, UserDetailWrap, UserInfoHeader, UserInfoWrap, Wrap } from './UserDetail.styled';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from 'store/modules/store';
import { UserAction, fetchUserProfile } from 'store/modules/user';
import { ThunkDispatch } from 'redux-thunk';

function UserDetail() {
  const { reviews, dispatchReviews, fetchNextReviews, afterParam, handleScroll } = useReviews();
  const myUserId = accountStore.id;
  const { pathname } = useLocation();
  const targetId = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = myUserId === targetId;
  const REVIEW_SIZE = 5;
  const userStore = useSelector((state: RootState) => state.user);
  const dispatch: ThunkDispatch<RootState, null, UserAction> = useDispatch();
  useAuth();

  useEffect(() => {
    dispatch(fetchUserProfile(targetId));
  }, []);

  useEffect(() => {
    const INIT_AFTER_PARAM = 0;
    fetchNextReviews(getUrl(INIT_AFTER_PARAM, REVIEW_SIZE, targetId));
  }, []);

  return (
    <Wrap
      onScroll={(e) => {
        handleScroll(e, getUrl(afterParam, REVIEW_SIZE, targetId));
      }}
    >
      <CommonHeader dispatchReviews={dispatchReviews} />
      <UserDetailWrap>
        <UserProfilePhoto src={userStore?.profileImage} />
        <UserInfoWrap>
          <UserInfoHeader>
            {userStore?.nickname}
            {!isMyUserDetailPage && <FollowButton />}
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {userStore?.reviewCount}</div>
            <div>팔로우 {userStore?.followingCount}</div>
            <div>팔로워 {userStore?.followersCount}</div>
          </ActivityInfoWrap>
        </UserInfoWrap>
      </UserDetailWrap>
      <Feeds reviews={reviews} />
    </Wrap>
  );
}

function getUrl(after, reviewSize, targetId) {
  return `api/users/${targetId}/reviews?after=${after}&size=${reviewSize}`;
}

export default UserDetail;
