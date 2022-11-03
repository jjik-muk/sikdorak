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
import ReviewWrite from 'pages/ReviewWrite/ReviewWrite';
import Portal from '../Portal/Portal';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { reloadBrowser } from 'utils/utils';

function KebabMenu({ reviewId, isMyFeed }: MenuProps) {
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const [isActiveModification, toggleIsActiveModification] = useToggle(false);
  const menuRef = useRef(null);
  const reviewWriteModalRef = useRef(null);
  const { dispatchReviews } = useReviews();
  const [, dispatchReviewWriteState] = useReviewWrite();

  useOutsideClick(menuRef, toggleIsActiveMenu);
  useOutsideClick(reviewWriteModalRef, () => {
    dispatchReviewWriteState({ type: 'RESET_STATE' });
    toggleIsActiveModification();
  });

  return (
    <div onClick={(e) => e.stopPropagation()}>
      {isMyFeed && (
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
      )}
      {isActiveModification && (
        <Portal selector="#portal" ref={reviewWriteModalRef}>
          <ReviewWrite toggleIsReviewWrite={toggleIsActiveModification} dispatchReviews={dispatchReviews} isModify reviewId={reviewId} />
        </Portal>
      )}
    </div>
  );

  async function handleModifyReview() {
    const { data } = await fetchData({ path: `api/reviews/${reviewId}` });
    dispatchReviewWriteState({ type: 'SET_DATE', ...convertToDateObj(data.visitedDate) });
    dispatchReviewWriteState({ type: 'TYPING_TEXT', text: data.reviewContent });
    dispatchReviewWriteState({ type: 'RATE', rating: data.reviewScore });
    dispatchReviewWriteState({ type: 'SET_SCOPE', scope: data.reviewVisibility });
    dispatchReviewWriteState({ type: 'SET_TAGS', tags: data.tags });
    dispatchReviewWriteState({ type: 'SET_IMAGES', images: data.images });
    dispatchReviewWriteState({ type: 'SET_STORE', store: data.store });

    toggleIsActiveModification();

    function convertToDateObj(visitedDate: string) {
      const [year, month, date] = visitedDate.split('-').map(Number);
      const dayIdx = new Date(year, month - 1, date).getDay();
      const days = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
      const day = days[dayIdx];
      return { year, month, date, day };
    }
  }

  async function handleDeleteReview() {
    await fetchData({ path: API_PATH.REVIEW.DELETE(reviewId), method: 'DELETE', withAccessToken: true });

    dispatchReviews({ type: 'DELETE_REVIEW', reviewId });
    reloadBrowser();
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
