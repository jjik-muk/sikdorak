import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import { useEffect, useReducer, useState } from 'react';
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

function reviewsReducer(state, action) {
  switch (action.type) {
    case 'SET_REVIEWS':
      return { reviews: action.reviews };
    case 'ADD_REVIEW':
      return { reviews: [...state.reviews, action.review] };
    default:
      throw new Error('reviewsReducer에 정의된 action type이 아닙니다.');
  }
}

function UserDetail() {
  const [{ reviews }, dispatchReviews] = useReducer(reviewsReducer, { reviews: [] });
  const [userProfile, setUserProfile] = useState(null);
  const [myUserInfo] = useMyUserInfo();
  const myUserId = myUserInfo.userId;
  const { pathname } = useLocation();
  const targetId = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = myUserId === targetId;

  useEffect(() => {
    fetchReviews();
    fetchUserInfo();

    async function fetchReviews() {
      const res = await fetchDataThatNeedToLogin(`api/users/${targetId}/reviews`);
      dispatchReviews({ type: 'SET_REVIEWS', reviews: res.data });
    }
    async function fetchUserInfo() {
      const res = await fetchDataThatNeedToLogin(`api/users/${targetId}`);
      setUserProfile(res.data);
    }
  }, [targetId]);

  return (
    <Wrap>
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
}

export default UserDetail;
