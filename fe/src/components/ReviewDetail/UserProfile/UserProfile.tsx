import { useNavigate } from 'react-router-dom';
import { Nickname, Picture, Wrap } from './UserProfile.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function UserProfile({ imgUrl = DEFAULT_IMG_URL, nickname, userId }: UserProfileProps) {
  const navigate = useNavigate();

  return (
    <Wrap
      onClick={(e) => {
        e.stopPropagation();
        navigate(`/user/${userId}`);
      }}
    >
      <Picture src={imgUrl} alt="Reviewer profile picture" />
      <Nickname>{nickname}</Nickname>
    </Wrap>
  );
}

export default UserProfile;

type UserProfileProps = {
  imgUrl?: string;
  nickname: string;
  userId?: number;
};
