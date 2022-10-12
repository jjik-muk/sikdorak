import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feeds from 'components/Common/Feeds/Feeds';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { ActivityInfoWrap, UserDetailWrap, UserInfoHeader, UserInfoWrap, Wrap } from './UserDetail.styled';

function UserDetail() {
  const { reviews, dispatchReviews, fetchNextReviews, afterParam, handleScroll } = useReviews();
  const [userProfile, setUserProfile] = useState(null);
  const myUserId = accountStore.id;
  const { pathname } = useLocation();
  const targetId = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = myUserId === targetId;
  const REVIEW_SIZE = 5;
  useAuth();

  useEffect(() => {
    fetchUserInfo();

    async function fetchUserInfo() {
      const res = await fetchDataThatNeedToLogin(`api/users/${targetId}`);
      setUserProfile(res.data);
    }
  }, [targetId]);
  useEffect(() => {
    fetchNextReviews(getUrl(afterParam, REVIEW_SIZE, targetId));
  });

  return (
    <Wrap
      onScroll={(e) => {
        handleScroll(e, getUrl(afterParam, REVIEW_SIZE, targetId));
      }}
    >
      <CommonHeader dispatchReviews={dispatchReviews} />
      <UserDetailWrap>
        <UserProfilePhoto src={userProfile?.profileImage} />
        <UserInfoWrap>
          <UserInfoHeader>
            {userProfile?.nickname}
            {!isMyUserDetailPage && <FollowButton alreadyFollowed={userProfile?.relationStatus?.followStatus} />}
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {userProfile?.reviewCount}</div>
            <div>팔로우 {userProfile?.followingCount}</div>
            <div>팔로워 {userProfile?.followersCount}</div>
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
