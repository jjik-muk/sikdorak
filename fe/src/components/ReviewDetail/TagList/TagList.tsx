import Chip from '@mui/material/Chip';
import { CommentWrapper, Picture, Wrap } from './TagList.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function TagList({ imgUrl = DEFAULT_IMG_URL, tags }: { imgUrl?: string; tags: string[] }) {
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
