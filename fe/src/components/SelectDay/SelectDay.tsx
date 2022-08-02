import BoxContainer from 'components/BoxContainer/BoxContainer';
import Calendar from 'components/Calendar/Calendar';
import { useState } from 'react';
import { SelectDayWrapper } from './SelectDay.styled';

export default function SelectDay() {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);

  const handleClick = () => {
    setIsCalendarOpen(!isCalendarOpen);
  };
  return (
    <BoxContainer>
      <div onClick={handleClick}>
        <SelectDayWrapper>방문일 선택</SelectDayWrapper>
      </div>
      {isCalendarOpen && <Calendar />}
    </BoxContainer>
  );
}
