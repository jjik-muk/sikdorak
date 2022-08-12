import { useState } from 'react';
import WriteComment from '../WriteComment/WriteComment';
import { Wrap, Picture, Title, Content, ContentWrapper, ButtonWrapper, Button, CommentWrapper } from './Comment.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';
const preventEvent = (e) => {
  e.preventDefault();
};

function Comment({ imgUrl = DEFAULT_IMG_URL, title = '식도락', content = '댓글 달아 봤습니다.' }: CommentProps) {
  const [isActiveAdditionComment, setIsActiveAdditionComment] = useState(false);

  return (
    <Wrap>
      <Picture src={imgUrl} />
      <CommentWrapper>
        <ContentWrapper>
          <Title>{title}</Title>
          <Content>{content}</Content>
        </ContentWrapper>
        <ButtonWrapper>
          <Button onClick={preventEvent}>좋아요</Button>
          <Button onClick={handleAdditionComment}>댓글 추가</Button>
        </ButtonWrapper>
        {isActiveAdditionComment && <WriteComment />}
      </CommentWrapper>
    </Wrap>
  );

  function handleAdditionComment() {
    if (isActiveAdditionComment) {
      setIsActiveAdditionComment(false);
      return;
    }
    setIsActiveAdditionComment(true);
  }
}

export default Comment;

interface CommentProps {
  imgUrl?: string;
  title?: string;
  content?: string;
}
