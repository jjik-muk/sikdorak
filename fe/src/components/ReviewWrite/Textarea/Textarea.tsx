import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { Input, Wrap } from './Textarea.styled';

function Textarea() {
  const [reviewWriteContext, dispatchReviewWriteState] = useReviewWrite();
  const [inputValue, setInputValue] = useState(reviewWriteContext.content);

  return (
    <Wrap>
      <Input
        value={inputValue}
        onChange={(e) => {
          setInputValue(e.target.value);
          dispatchReviewWriteState({ type: 'TYPING_TEXT', text: e.target.value });
        }}
        placeholder="텍스트를 입력해주세요."
      />
    </Wrap>
  );
}

export default Textarea;
