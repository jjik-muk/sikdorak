import { useNavigate } from 'react-router-dom';
import { Wrap, Picture, Title, Content, ContentWrapper, CommentWrapper } from './Comment.styled';

function Comment({ imgUrl, title, content, authorId }: CommentProps) {
  const navigate = useNavigate();

  return (
    <Wrap>
      <Picture
        src={imgUrl}
        onClick={() => {
          navigate(`/user/${authorId}`);
        }}
      />
      <CommentWrapper>
        <ContentWrapper>
          <Title
            onClick={() => {
              navigate(`/user/${authorId}`);
            }}
          >
            {title}
          </Title>
          <Content>{content}</Content>
        </ContentWrapper>
      </CommentWrapper>
    </Wrap>
  );
}

export default Comment;

type CommentProps = {
  imgUrl?: string;
  title?: string;
  content?: string;
  authorId?: number;
};
