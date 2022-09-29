import Chip from '@mui/material/Chip';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { createKey } from 'utils/utils';
import TagInput from '../TagInput/TagInput';
import { Wrap } from './TagContainer.styled';

function TagContainer() {
  const [reviewWriteState] = useReviewWrite();
  const { tags } = reviewWriteState;

  return (
    <Wrap>
      <TagInput />
      {tags.map((tag: string, idx: number) => (
        <div key={createKey(tag, idx)}>
          <TagItem idx={idx} value={tag} />
        </div>
      ))}
    </Wrap>
  );
}

export default TagContainer;

function TagItem({ idx, value }: any) {
  const [, dispatchReviewWrite] = useReviewWrite();
  return <Chip label={`#${value}`} variant="outlined" onDelete={removeTag} />;

  function removeTag() {
    dispatchReviewWrite({ type: 'REMOVE_TAG', tagIdx: idx });
  }
}
