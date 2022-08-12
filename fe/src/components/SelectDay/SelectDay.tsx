import Calendar from 'components/Calendar/Calendar';
import { useState } from 'react';
import { SelectDayWrapper, Wrap } from './SelectDay.styled';

export default function SelectDay() {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);

  const handleClick = () => {
    setIsCalendarOpen(!isCalendarOpen);
  };
  return (
    <Wrap>
      <div onClick={handleClick}>
        <SelectDayWrapper>방문일 선택</SelectDayWrapper>
      </div>
      {isCalendarOpen && <Calendar />}
    </Wrap>
  );
}
