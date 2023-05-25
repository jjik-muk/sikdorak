import { useNavigate } from 'react-router-dom';
import { Nickname, Picture, Wrap } from './UserProfile.styled';
import { GET_ALT } from 'constants/alt';
import { DEFAULT_USER_IMG } from 'constants/dummyData';

function UserProfile({ imgUrl = DEFAULT_USER_IMG, nickname, userId }: UserProfileProps) {
  const navigate = useNavigate();

  return (
    <Wrap
      onClick={(e) => {
        e.stopPropagation();
        navigate(`/user/${userId}`);
      }}
    >
      <Picture src={imgUrl} alt={GET_ALT.PROFILE(nickname)} />
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
