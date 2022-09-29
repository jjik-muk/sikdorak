import TextField from '@mui/material/TextField';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { debounce } from 'utils/utils';

function TagInput() {
  const [value, setValue] = useState('');
  const [, dispatchReviewWriteState] = useReviewWrite();
  return (
    <TextField
      id="tag-input"
      label="태그 입력해주세요."
      variant="outlined"
      value={value}
      onChange={(e) => setValue(e.target.value)}
      onKeyDown={debounce(handleTagAddition, 100)}
    />
  );

  function handleTagAddition(e) {
    const isPressEnter = e.key === 'Enter';
    const hasInputValue = value.length > 0;
    if (isPressEnter && hasInputValue) {
      dispatchReviewWriteState({ type: 'SET_TAGS', tags: `${value}` });
      setValue('');
    }
  }
}

export default TagInput;
