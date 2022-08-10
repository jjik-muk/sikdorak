import styled from 'styled-components';

function WriteComment() {
  return <Input placeholder="댓글을 남겨주세요..." />;
}

export default WriteComment;

const Input = styled.textarea`
  margin-top: 10px;
  width: 100%;
  height: 30px;
  padding: 7px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  border: 1px solid #fefefe;
  resize: none;
  overflow: hidden;
`;
