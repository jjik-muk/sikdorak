import BoxContainer from 'components/BoxContainer/BoxContainer';
import { useState } from 'react';
import { Wrap } from './Textarea.styled';

function Textarea() {
  const [value, setValue] = useState('');
  return (
    <BoxContainer>
      <Wrap value={value} onChange={handleChange} placeholder="텍스트를 입력해주세요." />
    </BoxContainer>
  );

  function handleChange(e) {
    setValue(e.target.value);
  }
}

export default Textarea;
