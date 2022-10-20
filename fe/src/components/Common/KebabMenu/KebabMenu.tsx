import CancelIcon from '@mui/icons-material/Cancel';
import DeleteIcon from '@mui/icons-material/Delete';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import CreateIcon from '@mui/icons-material/Create';
import { IconButton, MenuItem } from '@mui/material';
import { API_PATH } from 'constants/apiPath';
import { useOutsideClick } from 'hooks/useOutsideClick';
import useReviews from 'hooks/useReviews';
import useToggle from 'hooks/useToggle';
import { useRef } from 'react';
import { fetchData } from 'utils/fetch';
import { Wrap } from './KebabMenu.styled';

function KebabMenu({ reviewId, isMyFeed }: MenuProps) {
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const { dispatchReviews } = useReviews();
  const menuRef = useRef(null);
  useOutsideClick(menuRef, toggleIsActiveMenu);

  return (
    isMyFeed && (
      <div onClick={handleMenu} style={{ position: 'relative' }}>
        <IconButton aria-label="settings">
          <MoreVertIcon />
        </IconButton>
        {isActiveMenu && (
          <Wrap ref={menuRef}>
            <MenuItem onClick={handleDeleteReview}>
              <DeleteIcon sx={{ marginRight: '6px' }} />
              삭제
            </MenuItem>
            <MenuItem onClick={handleModifyReview}>
              <CreateIcon sx={{ marginRight: '6px' }} />
              수정
            </MenuItem>
            <MenuItem>
              <CancelIcon sx={{ marginRight: '6px' }} />
              취소
            </MenuItem>
          </Wrap>
        )}
      </div>
    )
  );

  function handleModifyReview() {
    console.log('리뷰수정 ');
  }

  function handleDeleteReview() {
    fetchData({ path: API_PATH.REVIEW.DELETE(reviewId), method: 'DELETE', withAccessToken: true });
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
