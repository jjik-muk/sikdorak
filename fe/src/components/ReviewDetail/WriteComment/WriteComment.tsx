import TextField from '@mui/material/TextField';
import { useState } from 'react';
import { accountStore } from 'stores/AccountStore';
import { fetchData } from 'utils/fetch';
import { openWarningToast } from 'utils/toast';

export default function WriteComment({ commentRef, reviewId, comments, setComments }: WriteCommentProps) {
  const [commentValue, setComment] = useState('');
  const { id, nickname, profileImage } = accountStore;

  return (
    <TextField
      fullWidth
      onChange={handleChange}
      onKeyDown={handleRegisterComment}
      value={commentValue}
      ref={commentRef}
      label="댓글 입력"
      variant="outlined"
      sx={{ marginTop: '20px' }}
    />
  );

  function handleRegisterComment(e) {
    const isPressedEnter = e.key === 'Enter';
    const hasInputValue = commentValue.length > 0;
    if (!isPressedEnter) return;
    if (!hasInputValue) return;

    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }

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
    fetchData({
      path: `api/reviews/${reviewId}/comments`,
      method: 'POST',
      bodyData: {
        content: commentValue,
      },
      withAccessToken: true,
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
