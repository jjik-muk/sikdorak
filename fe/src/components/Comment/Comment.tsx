import { Wrap, Picture, Title, Content, ContentWrapper, ButtonWrapper, Button, CommentWrapper } from './Comment.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';
const preventEvent = (e) => {
  e.preventDefault();
};

function Comment({ imgUrl = DEFAULT_IMG_URL, title = '식도락', content = '댓글 달아 봤습니다.' }: CommentProps) {
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
          <Button onClick={(e) => preventEvent(e)}>댓글 추가</Button>
        </ButtonWrapper>
      </CommentWrapper>
    </Wrap>
  );
}

export default Comment;

interface CommentProps {
  imgUrl?: string;
  title?: string;
  content?: string;
}
