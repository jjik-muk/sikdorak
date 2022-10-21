import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import { Wrap, Picture, Title, Content, ContentWrapper, CommentWrapper } from './Comment.styled';

function Comment({ imgUrl, title, content, authorId }: CommentProps) {
  const navigate = useNavigate();
  const isMyComment = accountStore.id === authorId;

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
        {isMyComment && <Button variant="text">수정</Button>}
        {isMyComment && <Button variant="text">삭제</Button>}
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
