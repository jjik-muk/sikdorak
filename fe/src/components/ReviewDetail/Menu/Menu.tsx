import { Btn, DeleteBtn, Wrap } from './Menu.styled';

function Menu() {
  return (
    <Wrap>
      <DeleteBtn>삭제</DeleteBtn>
      <Btn>수정</Btn>
      <Btn>취소</Btn>
    </Wrap>
  );
}

export default Menu;
