import { Button, Input } from '@mui/material';
import useToggle from 'hooks/useToggle';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { accountStore } from 'stores/AccountStore';
import { fetchData } from 'utils/fetch';
import { Wrap, Picture, Title, Content, ContentWrapper, CommentWrapper } from './Comment.styled';

function Comment({ imgUrl, commentId, reviewId, title, content, authorId }: CommentProps) {
  const navigate = useNavigate();
  const isMyComment = accountStore.id === authorId;
  const [modificationComment, setModificationComment] = useState(content);
  const [isModificationMode, toggleIsModification] = useToggle(false);

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
          {isModificationMode ? <Input fullWidth value={modificationComment} onChange={handleChangeModificationInput} /> : <Content>{modificationComment}</Content>}
        </ContentWrapper>
        {!isModificationMode && isMyComment && (
          <Button variant="text" onClick={toggleIsModification}>
            수정
          </Button>
        )}
        {!isModificationMode && isMyComment && <Button variant="text">삭제</Button>}
        {isModificationMode && (
          <Button variant="text" onClick={handleModifyComment}>
            완료
          </Button>
        )}
        {isModificationMode && (
          <Button variant="text" onClick={toggleIsModification}>
            취소
          </Button>
        )}
      </CommentWrapper>
    </Wrap>
  );

  function handleChangeModificationInput(e) {
    setModificationComment(e.target.value);
  }

  function handleModifyComment() {
    fetchData({
      path: `api/reviews/${reviewId}/comments/${commentId}`,
      method: 'PUT',
      bodyData: {
        content: modificationComment,
      },
      withAccessToken: true,
    });
    toggleIsModification();
  }
}

export default Comment;

type CommentProps = {
  imgUrl?: string;
  commentId?: number;
  reviewId?: number;
  title?: string;
  content?: string;
  authorId?: number;
};
