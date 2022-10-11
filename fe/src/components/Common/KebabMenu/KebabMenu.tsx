import MoreVertIcon from '@mui/icons-material/MoreVert';
import { IconButton } from '@mui/material';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useReviews from 'hooks/useReviews';
import useToggle from 'hooks/useToggle';
import { useRef } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';
import { Btn, DeleteBtn, Wrap } from './KebabMenu.styled';

function KebabMenu({ reviewId, isMyFeed }: MenuProps) {
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const { dispatchReviews } = useReviews();
  const menuRef = useRef(null);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  return (
    isMyFeed && (
      <div onClick={handleMenu}>
        <IconButton aria-label="settings">
          <MoreVertIcon />
        </IconButton>
        {isActiveMenu && (
          <Wrap ref={menuRef}>
            <DeleteBtn onClick={handleDeleteReview}>삭제</DeleteBtn>
            <Btn>취소</Btn>
          </Wrap>
        )}
      </div>
    )
  );

  function handleDeleteReview() {
    fetchDataThatNeedToLogin(`api/reviews/${reviewId}`, { method: 'DELETE' });
    dispatchReviews({ type: 'DELETE_REVIEW', reviewId });
  }

  function handleMenu(e) {
    toggleIsActiveMenu();
    e.stopPropagation();
  }
}

export default KebabMenu;

type MenuProps = {
  reviewId: number;
  isMyFeed: boolean;
};
