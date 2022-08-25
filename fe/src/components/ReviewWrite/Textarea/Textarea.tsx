import { useReviewWrite } from 'context/reviewWriteProvider';
import { debounce } from 'utils/utils';
// import { useState } from 'react';
import { Input, Wrap } from './Textarea.styled';

function Textarea() {
  const [, dispatchReviewWriteState] = useReviewWrite();

  return (
    <Wrap>
      <Input onChange={debounce(handleChange, 100)} placeholder="텍스트를 입력해주세요." />
    </Wrap>
  );

  function handleChange({ target }) {
    dispatchReviewWriteState({ type: 'TYPING_TEXT', text: target.value });
  }
}

export default Textarea;
