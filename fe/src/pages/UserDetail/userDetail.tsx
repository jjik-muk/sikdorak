import { DOMAIN } from 'constants/dummyData';
import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { fetchDataThatNeedToLogin } from 'utils/utils';
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
  const [userProfile, setUserProfile] = useState(null);
  const [myInfo] = useMyUserInfo();
  const { userId } = myInfo;
  const { pathname } = useLocation();
  const ID = Number(pathname.split('/').at(-1));
  const isMyUserDetailPage = userId === ID;

  useEffect(() => {
    fetchAndStoreData({ url: `${DOMAIN}/api/users/${ID}/reviews`, dispatch: setReviews });
    fetchAndStoreData({ url: `${DOMAIN}/api/users/${ID}`, dispatch: setUserProfile });

    async function fetchAndStoreData({ url, dispatch }) {
      const res = await fetchDataThatNeedToLogin(url);
      const reeData = res.data;
      dispatch(reeData);
    }
  }, []);

  return (
    <Wrap>
      <CommonHeader />
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
      <FeedWrap>
        <Feeds reviews={reviews} />
      </FeedWrap>
    </Wrap>
  );
}

export default UserDetail;
