import Chip from '@mui/material/Chip';
import { CommentWrapper, Picture, Wrap } from './TagList.styled';

function TagList({ imgUrl, tags }: { imgUrl?: string; tags: string[] }) {
  return (
    <Wrap>
      <Picture src={imgUrl} />
      <CommentWrapper>
        {tags.map((tag) => (
          <Chip label={`#${tag}`} variant="outlined" sx={{ margin: '5px 5px 5px 0' }} />
        ))}
      </CommentWrapper>
    </Wrap>
  );
}

export default TagList;
