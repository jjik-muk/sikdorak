import TextField from '@mui/material/TextField';
import { MESSAGE } from 'constants/message';
import useComment from 'hooks/useComment';
import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from 'store/modules/store';
import { openWarningToast } from 'utils/toast';

export default function WriteComment({ commentRef, reviewId, fetchAndSetComments }: WriteCommentProps) {
  const [commentValue, setComment] = useState('');
  const { requestAddComment, isSubmitted, setIsSubmitted } = useComment({ reviewId });
  const accountStore = useSelector((state: RootState) => state.account);

  useEffect(() => {
    if (isSubmitted) {
      fetchAndSetComments({ saveMethod: 'OVERWRITE' });
      setIsSubmitted(false);
    }
  }, [isSubmitted]);

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
      openWarningToast(MESSAGE.ERROR.NEED_LOGIN);
      return;
    }

    requestAddComment({ commentValue });
    setComment('');
  }

  function handleChange(e) {
    setComment(e.target.value);
  }
}

type WriteCommentProps = {
  commentRef?: React.Ref<HTMLInputElement>;
  reviewId: number;
  fetchAndSetComments: Function;
};
