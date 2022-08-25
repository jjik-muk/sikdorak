import { Wrap } from './FollowButton.styled';

function FollowButton({ already }: FollowButtonProps) {
  return <Wrap>{already ? '언팔로우' : '팔로우'}</Wrap>;
}

type FollowButtonProps = {
  already?: boolean;
};

export default FollowButton;
