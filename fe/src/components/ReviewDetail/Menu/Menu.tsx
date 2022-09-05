import { Ref } from 'react';
import { Btn, DeleteBtn, Wrap } from './Menu.styled';

function Menu({ menuRef }: MenuProps) {
  return (
    <Wrap ref={menuRef}>
      <DeleteBtn>삭제</DeleteBtn>
      <Btn>수정</Btn>
      <Btn>취소</Btn>
    </Wrap>
  );
}

export default Menu;

type MenuProps = {
  menuRef: Ref<HTMLDivElement>;
};
