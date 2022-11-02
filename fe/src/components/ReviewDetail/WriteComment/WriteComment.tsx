import TextField from '@mui/material/TextField';
import useComment from 'hooks/useComment';
import { useState } from 'react';
import { accountStore } from 'stores/AccountStore';
import { openWarningToast } from 'utils/toast';

export default function WriteComment({ commentRef, reviewId, fetchAndSetComments }: WriteCommentProps) {
  const [commentValue, setComment] = useState('');
  const { requestAddComment } = useComment({ reviewId });

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

  async function handleRegisterComment(e) {
    const isPressedEnter = e.key === 'Enter';
    const hasInputValue = commentValue.length > 0;
    if (!isPressedEnter) return;
    if (!hasInputValue) {
      openWarningToast('댓글 내용을 입력해주세요.');
      return;
    }
    if (!accountStore.id) {
      openWarningToast('로그인이 필요한 서비스입니다. 로그인 해 주세요.');
      return;
    }

    requestAddComment({ commentValue });
    setComment('');
    const DELAY_MS = 100;
    setTimeout(() => {
      fetchAndSetComments({ saveMethod: 'OVERWRITE' });
    }, DELAY_MS);
  }

  function handleChange(e) {
    setComment(e.target.value);
  }
}

// TODO: Dispatch 타입 정의

type WriteCommentProps = {
  commentRef?: React.Ref<HTMLInputElement>;
  reviewId: number;
  fetchAndSetComments: Function;
};

// type Comment = {
//   id: number;
//   content: string;
//   author: {
//     id: number;
//     nickname: string;
//     profileImage: string;
//   };
// };
