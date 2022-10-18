import Chip from '@mui/material/Chip';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import styled from 'styled-components';
import { createKey } from 'utils/utils';
import TagInput from '../TagInput/TagInput';
import { Wrap } from './TagContainer.styled';

function TagContainer() {
  const [reviewWriteState] = useReviewWrite();
  const { tags } = reviewWriteState;

  return (
    <Wrap>
      <TagInput />
      <Tags>
        {tags.map((tag: string, idx: number) => (
          <span key={createKey(tag, idx)} style={{ whiteSpace: 'nowrap' }}>
            <TagItem idx={idx} value={tag} />
          </span>
        ))}
      </Tags>
    </Wrap>
  );
}

export default TagContainer;

function TagItem({ idx, value }: any) {
  const [, dispatchReviewWrite] = useReviewWrite();
  return <Chip label={`#${value}`} variant="outlined" onDelete={removeTag} sx={{ margin: '10px 5px 0 0' }} />;

  function removeTag() {
    dispatchReviewWrite({ type: 'REMOVE_TAG', tagIdx: idx });
  }
}

const Tags = styled.div`
  display: inline-block;
  width: 300px;
  height: fit-content;
  position: relative;
  vertical-align: top;
`;
