import { useReviewWrite } from 'context/reviewWriteProvider';
import { createKey } from 'utils/utils';
import TagInput from '../TagInput/TagInput';
import TagItem from '../TagItem/TagItem';
import { Wrap } from './TagContainer.styled';

function TagContainer() {
  const [reviewWriteState] = useReviewWrite();
  const { tags } = reviewWriteState;

  return (
    <Wrap>
      {tags.map((tag, idx) => (
        <TagItem key={createKey(tag, idx)} name={tag} />
      ))}
      <TagInput />
    </Wrap>
  );
}

export default TagContainer;
