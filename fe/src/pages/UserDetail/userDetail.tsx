import { DOMAIN } from 'constants/dummyData';
import CommonHeader from 'components/CommonHeader/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import {
  ActivityInfoWrap,
  FeedWrap,
  ProfileInfoWrap,
  UserDetailWrap,
  UserInfoHeader,
  UserInfoWrap,
  Wrap,
} from './UserDetail.styled';

function UserDetail() {
  const [reviews, setReviews] = useState([]);
  const [reviewCnt, setReviewCnt] = useState(0);
  const [followerCnt, setFollowerCnt] = useState(0);
  const [followingCnt, setFollowingCnt] = useState(0);
  const myInfo = JSON.parse(localStorage.getItem('MY_INFO'));
  const { userId, nickname, profileImageUrl } = myInfo;
  const { pathname } = useLocation();
  const ID = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = userId === ID;

  useEffect(() => {
    fetchReviewDetail();
    fetchMyFollower();
    fetchMyFollowing();

    // TODO: 중복 코드 제거
    async function fetchReviewDetail() {
      const reviewDetailRes = await fetchDataThatNeedToLogin(`${DOMAIN}/api/users/${userId}/reviews`);
      const fetchedReviews = reviewDetailRes.data;
      setReviews(fetchedReviews);
      setReviewCnt(fetchedReviews.length);
    }
    async function fetchMyFollower() {
      const myFollowerRes = await fetchDataThatNeedToLogin(`${DOMAIN}/api/users/${userId}/followers`);
      const fetchedMyFollower = myFollowerRes.data;
      setFollowerCnt(fetchedMyFollower.length);
    }
    async function fetchMyFollowing() {
      const myFollowingRes = await fetchDataThatNeedToLogin(`${DOMAIN}/api/users/${userId}/followings`);
      const fetchedMyFollowing = myFollowingRes.data;
      setFollowingCnt(fetchedMyFollowing.length);
    }
  }, []);

  return (
    <Wrap>
      <CommonHeader />
      <UserDetailWrap>
        <UserProfilePhoto src={profileImageUrl} />
        <UserInfoWrap>
          <UserInfoHeader>
            {nickname}
            {!isMyUserDetailPage && <FollowButton />}
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {reviewCnt}</div>
            <div>팔로우 {followingCnt}</div>
            <div>팔로워 {followerCnt}</div>
          </ActivityInfoWrap>
          <ProfileInfoWrap>자신을 소개해주세요.</ProfileInfoWrap>
        </UserInfoWrap>
      </UserDetailWrap>
      <FeedWrap>
        {reviews &&
          reviews.map(({ reviewContent, images }, idx) => (
            <Feed
              key={createKey(myInfo.nickname, idx)}
              author={myInfo.nickname}
              contents={reviewContent}
              store={{ name: '호이 초밥', region: '부산' }}
              likeCnt={0}
              pictures={images}
            />
          ))}
      </FeedWrap>
    </Wrap>
  );
}

export default UserDetail;
