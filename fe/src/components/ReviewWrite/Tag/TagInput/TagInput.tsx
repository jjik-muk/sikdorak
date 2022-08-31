import { useReviewWrite } from 'context/reviewWriteProvider';
import { useState } from 'react';
import { debounce } from 'utils/utils';
import { Input } from './TagInput.styled';

function TagInput() {
  const [value, setValue] = useState('');
  const [, dispatchReviewWriteState] = useReviewWrite();
  return (
    <Input
      value={value}
      onChange={(e) => setValue(e.target.value)}
      onKeyDown={debounce(handleTagAddition, 100)}
      placeholder="#태그 입력"
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
