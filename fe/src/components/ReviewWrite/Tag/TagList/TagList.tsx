import { useId } from 'react';
import { createKey } from 'utils/utils';
import { CommentWrapper, ContentWrapper, Picture, Wrap } from './TagList.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function TagList({ imgUrl = DEFAULT_IMG_URL, tags }: { imgUrl?: string; tags: string[] }) {
  const id = useId();

  return (
    <Wrap>
      <Picture src={imgUrl} />
      <CommentWrapper>
        <ContentWrapper>
          {tags.map((tag, idx) => (
            <div key={createKey(id, idx)}>{tag}</div>
          ))}
        </ContentWrapper>
      </CommentWrapper>
    </Wrap>
  );
}

export default TagList;
