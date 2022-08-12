import { useState } from 'react';
import TagInput from '../TagInput/TagInput';
import TagItem from '../TagItem/TagItem';
import { Wrap } from './TagContainer.styled';

function TagContainer() {
  const [tags, setTags] = useState([]);

  return (
    <Wrap>
      {tags.map((tag) => (
        <TagItem name={tag} />
      ))}
      <TagInput tags={tags} setTags={setTags} />
    </Wrap>
  );
}

export default TagContainer;
