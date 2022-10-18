import TextField from '@mui/material/TextField';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { Wrap } from './Textarea.styled';

function Textarea() {
  const [reviewWriteContext, dispatchReviewWriteState] = useReviewWrite();
  const [inputValue, setInputValue] = useState(reviewWriteContext.content);
  const MIN_ROWS = 5;

  return (
    <Wrap>
      <TextField
        fullWidth
        multiline
        minRows={MIN_ROWS}
        id="review-content-input"
        label="리뷰 내용 입력"
        variant="outlined"
        value={inputValue}
        onChange={(e) => {
          setInputValue(e.target.value);
          dispatchReviewWriteState({ type: 'TYPING_TEXT', text: e.target.value });
        }}
      />
    </Wrap>
  );
}

export default Textarea;
