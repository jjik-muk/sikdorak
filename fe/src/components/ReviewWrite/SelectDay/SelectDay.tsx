import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import Calendar from 'components/ReviewWrite/Calendar/Calendar';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { Wrap } from './SelectDay.styled';

export default function SelectDay() {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);
  const [reviewState] = useReviewWrite();
  const { year, month, date, day } = reviewState;

  const openCalendar = () => {
    setIsCalendarOpen(!isCalendarOpen);
  };

  return (
    <Wrap>
      <Accordion expanded={isCalendarOpen} onChange={openCalendar}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1bh-content" id="panel1bh-header">
          <Typography>{year ? <div>{`${year}년 ${month}월 ${date}일 (${day})요일`}</div> : '방문일 선택'}</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Calendar setIsCalendarOpen={setIsCalendarOpen} />
        </AccordionDetails>
      </Accordion>
    </Wrap>
  );
}
