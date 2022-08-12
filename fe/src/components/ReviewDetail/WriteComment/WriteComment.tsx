import { Input } from './WriteComment.styled';

export default function WriteComment({ commentRef }: { commentRef?: React.MutableRefObject<HTMLTextAreaElement> }) {
  return <Input ref={commentRef} placeholder="댓글을 남겨주세요..." />;
}
