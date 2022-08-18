import CommonHeader from 'components/Common/CommonHeader';
import FollowButton from 'components/UserDetail/FollowButton/FollowButton';
import UserProfilePhoto from 'components/UserDetail/UserProfilePhoto/UserProfilePhoto';
import {
  ActivityInfoWrap,
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
  return (
    <Wrap>
      <CommonHeader />
      <UserDetailWrap>
        <UserProfilePhoto src={MockUserInfo.profileImg} />
        <UserInfoWrap>
          <UserInfoHeader>
            {MockUserInfo.name}
            <FollowButton />
          </UserInfoHeader>
          <ActivityInfoWrap>
            <div>게시물 {MockUserInfo.postCnt}</div>
            <div>팔로우 {MockUserInfo.followCnt}</div>
            <div>팔로워 {MockUserInfo.followerCnt}</div>
          </ActivityInfoWrap>
          <ProfileInfoWrap>
            {MockUserInfo.name}
            {MockUserInfo.profileMessage}
          </ProfileInfoWrap>
        </UserInfoWrap>
      </UserDetailWrap>
    </Wrap>
  );
}

export default UserDetail;
