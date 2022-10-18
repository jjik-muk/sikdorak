import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Switch from '@mui/material/Switch';
import Typography from '@mui/material/Typography';
import { TEXT } from 'constants/text';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { Wrap } from './PostScope.styled';

export default function PostScope() {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [reviewWriteState, dispatchReviewWriteState] = useReviewWrite();

  const { scope: currentScope } = reviewWriteState;
  const isPublicScope = currentScope === 'PUBLIC';

  const handleSetModal = () => {
    setIsOpenModal(!isOpenModal);
  };

  const handleSetScope = () => {
    const nextScope = isPublicScope ? 'PRIVATE' : 'PUBLIC';
    dispatchReviewWriteState({ type: 'SET_SCOPE', scope: nextScope });
  };
  return (
    <Wrap>
      <Accordion expanded={isOpenModal} onChange={handleSetModal}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1bh-content" id="panel1bh-header">
          <Typography>게시물 공개 범위</Typography>
        </AccordionSummary>
        <AccordionDetails sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Typography>{TEXT.SCOPE[currentScope]}</Typography>
          <Switch onChange={handleSetScope} />
        </AccordionDetails>
      </Accordion>
    </Wrap>
  );
}
