import { PhotoImg } from './UserProfilePhoto.styled';

function UserProfilePhoto({ src }: UserProfileProps) {
  return <PhotoImg src={src} />;
}

type UserProfileProps = {
  src: string;
};

export default UserProfilePhoto;
