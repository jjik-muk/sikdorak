import { DOMAIN } from 'constants/dummyData';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import { useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Input } from './WriteComment.styled';

export default function WriteComment({ commentRef, reviewId, comments, setComments }: WriteCommentProps) {
  const [commentValue, setComment] = useState('');
  const [myUserInfo] = useMyUserInfo();
  const { userId, nickname, profileImageUrl } = myUserInfo;
  return (
    <Input
      onChange={handleChange}
      onKeyDown={handleRegisterComment}
      value={commentValue}
      ref={commentRef}
      placeholder="댓글을 남겨주세요..."
    />
  );

  function handleRegisterComment(e) {
    const isPressedEnter = e.key === 'Enter';
    if (!isPressedEnter) return;

    postWrittingComment();
    setComment('');

    // TODO: 서버에서 200 응답 보냈을 경우에만 클라이언트에 상태 추가

    const newComment = {
      id: reviewId,
      content: commentValue,
      author: {
        id: userId,
        nickname,
        profileImage: profileImageUrl,
      },
    };

    setComments([...comments, newComment]);
  }

  async function postWrittingComment() {
    fetchDataThatNeedToLogin(`${DOMAIN}/api/reviews/${reviewId}/comments`, {
      method: 'POST',
      bodyData: {
        content: commentValue,
      },
    });
  }

  function handleChange(e) {
    setComment(e.target.value);
  }
}

// TODO: Dispatch 타입 정의

type WriteCommentProps = {
  commentRef?: React.MutableRefObject<HTMLTextAreaElement>;
  reviewId: number;
  comments: string[];
  setComments: any;
};
