import { CommentWrapper, ContentWrapper, Picture, Wrap } from './TagList.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function TagList({ imgUrl = DEFAULT_IMG_URL, tags }: { imgUrl?: string; tags: string[] }) {
  return (
    <Wrap>
      <Picture src={imgUrl} />
      <CommentWrapper>
        <ContentWrapper>
          {tags.map((tag) => (
            <div>{tag}</div>
          ))}
        </ContentWrapper>
      </CommentWrapper>
    </Wrap>
  );
}

export default TagList;
