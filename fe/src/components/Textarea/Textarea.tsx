import { useState } from 'react';
import { Wrap } from './Textarea.styled';

function Textarea() {
  const [value, setValue] = useState('');
  return <Wrap value={value} onChange={handleChange} placeholder="텍스트를 입력해주세요." />;

  function handleChange(e) {
    setValue(e.target.value);
  }
}

export default Textarea;
