import useReviews from 'hooks/useReviews';
import { Ref } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Btn, DeleteBtn, Wrap } from './Menu.styled';

function Menu({ menuRef, reviewId }: MenuProps) {
  const { dispatchReviews } = useReviews();

  return (
    <Wrap ref={menuRef}>
      <DeleteBtn onClick={handleDeleteReview}>삭제</DeleteBtn>
      <Btn onClick={handleModifyReview}>수정</Btn>
      <Btn>취소</Btn>
    </Wrap>
  );

  function handleModifyReview() {
    console.log('수정');
  }

  function handleDeleteReview() {
    fetchDataThatNeedToLogin(`api/reviews/${reviewId}`, { method: 'DELETE' });
    dispatchReviews({ type: 'DELETE_REVIEW', reviewId });
  }
}

export default Menu;

type MenuProps = {
  menuRef: Ref<HTMLDivElement>;
  reviewId: number;
};
