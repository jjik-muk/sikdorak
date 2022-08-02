import { Nickname, Picture, Wrap } from './Profile.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function Profile({ imgUrl = DEFAULT_IMG_URL, nickname }: ProfileProps) {
  return (
    <Wrap>
      <Picture src={imgUrl} />
      <Nickname>{nickname}</Nickname>
    </Wrap>
  );
}

export default Profile;

interface ProfileProps {
  imgUrl?: string;
  nickname: string;
}
