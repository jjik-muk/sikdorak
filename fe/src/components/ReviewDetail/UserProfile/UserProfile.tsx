import { Nickname, Picture, Wrap } from './UserProfile.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function UserProfile({ imgUrl = DEFAULT_IMG_URL, nickname }: UserProfileProps) {
  return (
    <Wrap>
      <Picture src={imgUrl} />
      <Nickname>{nickname}</Nickname>
    </Wrap>
  );
}

export default UserProfile;

interface UserProfileProps {
  imgUrl?: string;
  nickname: string;
}
