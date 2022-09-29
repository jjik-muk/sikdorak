import { useState } from 'react';
import { accountStore } from 'stores/AccountStore';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Input } from './WriteComment.styled';

export default function WriteComment({ commentRef, reviewId, comments, setComments }: WriteCommentProps) {
  const [commentValue, setComment] = useState('');
  const { id, nickname, profileImage } = accountStore;
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
    const hasInputValue = commentValue.length > 0;
    if (!isPressedEnter) return;
    if (!hasInputValue) return;

    postWrittingComment();
    setComment('');

    // TODO: 서버에서 200 응답 보냈을 경우에만 클라이언트에 상태 추가

    const newComment = {
      id: reviewId,
      content: commentValue,
      author: {
        id,
        nickname,
        profileImage,
      },
    };

    setComments([...comments, newComment]);
  }

  async function postWrittingComment() {
    fetchDataThatNeedToLogin(`api/reviews/${reviewId}/comments`, {
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
  commentRef?: React.Ref<HTMLInputElement>;
  reviewId: number;
  comments: string[];
  setComments: any;
};
