import { Dispatch, SetStateAction, useState } from 'react';
import { Input } from './TagInput.styled';

function TagInput({ tags, setTags }: { tags: string[]; setTags: Dispatch<SetStateAction<any[]>> }) {
  const [value, setValue] = useState('');
  return (
    <Input
      value={value}
      onChange={(e) => setValue(e.target.value)}
      onKeyDown={handleTagAddition}
      placeholder="#태그 입력"
    />
  );

  function handleTagAddition(e) {
    if (e.key === 'Enter') {
      setTags([...tags, `#${value}`]);
      setValue('');
    }
  }
}

export default TagInput;
