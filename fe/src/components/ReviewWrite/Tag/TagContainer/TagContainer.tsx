import { useReviewWrite } from 'context/reviewWriteProvider';
import { createKey } from 'utils/utils';
import TagInput from '../TagInput/TagInput';
import { Item, Wrap } from './TagContainer.styled';

function TagContainer() {
  const [reviewWriteState] = useReviewWrite();
  const { tags } = reviewWriteState;

  return (
    <Wrap>
      {tags.map((tag: string, idx: number) => (
        <Item key={createKey(tag, idx)}>#{tag}</Item>
      ))}
      <TagInput />
    </Wrap>
  );
}

export default TagContainer;
