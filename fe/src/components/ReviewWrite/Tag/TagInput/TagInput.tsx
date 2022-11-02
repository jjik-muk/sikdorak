import TextField from '@mui/material/TextField';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { openWarningToast } from 'utils/toast';
import { debounce } from 'utils/utils';

function TagInput() {
  const [value, setValue] = useState('');
  const [, dispatchReviewWriteState] = useReviewWrite();
  const DELAY_MS = 100;
  return (
    <TextField
      id="tag-input"
      label="태그 입력"
      variant="outlined"
      fullWidth
      value={value}
      onChange={(e) => setValue(e.target.value)}
      onKeyDown={debounce(handleTagAddition, DELAY_MS)}
    />
  );

  function handleTagAddition(e) {
    const isPressEnter = e.key === 'Enter';
    const hasInputValue = value.length > 0;

    if (!isPressEnter) return;
    if (!hasInputValue) {
      openWarningToast('태그를 입력해주세요.');
      return;
    }
    if (!validateTag(value)) {
      openWarningToast('태그에 공백, 특수문자를 사용할 수 없습니다.');
      return;
    }
    dispatchReviewWriteState({ type: 'ADD_TAG', tags: `${value}` });
    setValue('');
  }

  function validateTag(tag: string) {
    const allowedTagExp = /[^a-z|0-9|ㄱ-ㅎ|가-힣]/gi;
    const res = tag.match(allowedTagExp);
    return res === null;
  }
}

export default TagInput;
