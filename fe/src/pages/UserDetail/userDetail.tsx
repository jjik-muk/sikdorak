import { DOMAIN } from 'constants/dummyData';
import CommonHeader from 'components/Common/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import { useEffect, useState } from 'react';
import { createKey, fetchDataThatNeedToLogin } from 'utils/utils';
import {
  ActivityInfoWrap,
  FeedWrap,
  ProfileInfoWrap,
  UserDetailWrap,
  UserInfoHeader,
  UserInfoWrap,
  Wrap,
} from './userDetail.styled';

const MockUserInfo = {
  name: 'Dashawn',
  postCnt: 10,
  followCnt: 1101,
  followerCnt: 333,
  profileMessage: '맛잘알 리스트는 아닙니다.. 그냥 기록용 입니다',
  profileImg: 'https://avatars.githubusercontent.com/u/87521172?v=4',
};

function UserDetail() {
  const [reviews, setReviews] = useState([]);
  const [reviewCnt, setReviewCnt] = useState(0);
  const myInfo = JSON.parse(localStorage.getItem('MY_INFO'));
  const { userId, nickname, profileImageUrl } = myInfo;

  useEffect(() => {
    fetchReviewDetail();

    async function fetchReviewDetail() {
      const reviewDetailRes = await fetchDataThatNeedToLogin(`${DOMAIN}/api/users/${userId}/reviews`);
      const fetchedReviews = reviewDetailRes.data;
      setReviews(fetchedReviews);
      setReviewCnt(fetchedReviews.length);
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
            <FollowButton />
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {reviewCnt}</div>
            <div>팔로우 {MockUserInfo.followCnt}</div>
            <div>팔로워 {MockUserInfo.followerCnt}</div>
          </ActivityInfoWrap>
          <ProfileInfoWrap>
            {MockUserInfo.name}
            {MockUserInfo.profileMessage}
          </ProfileInfoWrap>
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
