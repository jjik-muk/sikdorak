import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import {
  ActivityInfoWrap,
  ProfileInfoWrap,
  UserDetailWrap,
  UserInfoHeader,
  UserInfoWrap,
  Wrap,
} from './UserDetail.styled';

function UserDetail() {
  const { reviews, dispatchReviews, fetchNextReviews, afterParam, handleScroll } = useReviews();
  const [userProfile, setUserProfile] = useState(null);
  const [myUserInfo] = useMyUserInfo();
  const myUserId = myUserInfo.userId;
  const { pathname } = useLocation();
  const targetId = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = myUserId === targetId;
  const REVIEW_SIZE = 5;
  useAuth();

  useEffect(() => {
    fetchUserInfo();
    fetchNextReviews(getUrl(afterParam, REVIEW_SIZE));

    async function fetchUserInfo() {
      const res = await fetchDataThatNeedToLogin(`api/users/${targetId}`);
      setUserProfile(res.data);
    }
  }, [targetId]);

  return (
    <Wrap
      onScroll={(e) => {
        handleScroll(e, getUrl(afterParam, REVIEW_SIZE));
      }}
    >
      <CommonHeader dispatchReviews={dispatchReviews} />
      <UserDetailWrap>
        <UserProfilePhoto src={userProfile?.profileImage} />
        <UserInfoWrap>
          <UserInfoHeader>
            {userProfile?.nickname}
            {!isMyUserDetailPage && <FollowButton />}
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {userProfile?.reviewCount}</div>
            <div>팔로우 {userProfile?.followingCount}</div>
            <div>팔로워 {userProfile?.followersCount}</div>
          </ActivityInfoWrap>
          <ProfileInfoWrap>자신을 소개해주세요.</ProfileInfoWrap>
        </UserInfoWrap>
      </UserDetailWrap>
      <Feeds reviews={reviews} />
    </Wrap>
  );

  function getUrl(after, reviewSize) {
    return `api/users/${targetId}/reviews?after=${after}&size=${reviewSize}`;
  }
}

export default UserDetail;
