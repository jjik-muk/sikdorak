import Calendar from 'components/ReviewWrite/Calendar/Calendar';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { SelectDayWrapper, Wrap } from './SelectDay.styled';

export default function SelectDay() {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);
  const [reviewState] = useReviewWrite();
  const { year, month, date, day } = reviewState;

  const openCalendar = () => {
    setIsCalendarOpen(!isCalendarOpen);
  };

  return (
    <Wrap>
      <div onClick={openCalendar}>
        <SelectDayWrapper>
          {!year ? '방문일 선택' : <div>{`${year}년 ${month}월 ${date}일 (${day})요일`}</div>}
        </SelectDayWrapper>
      </div>
      {isCalendarOpen && <Calendar setIsCalendarOpen={setIsCalendarOpen} />}
    </Wrap>
  );
}
