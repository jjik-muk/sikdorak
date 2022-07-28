import { useEffect, useState } from 'react';
import { CalendarHead, Day, DayWrapper, Title, Wrap } from './Calendar.styled';

const MONTH_LIST = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
const dayList = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
const createKey = (value, idx) => `${value}-${idx}`;

export default function Calendar() {
  const todayDate = new Date();
  const [todayYear, todayMonth] = [todayDate.getFullYear(), todayDate.getMonth()];
  const [calendarDate, setCalendarDate] = useState(new Date(todayYear, todayMonth));
  const [allDayInfoForRender, setAllDayInfoForRender] = useState([]);

  const handleCalendarDate = (type: 'next' | 'prev') => {
    if (type === 'next') {
      const [nextYear, nextMonth] = [calendarDate.getFullYear(), calendarDate.getMonth() + 1];
      setCalendarDate(new Date(nextYear, nextMonth));
      return;
    }

    const [prevYear, prevMonth] = [calendarDate.getFullYear(), calendarDate.getMonth() - 1];
    setCalendarDate(new Date(prevYear, prevMonth));
  };

  useEffect(() => {
    const firstDay = new Date(calendarDate.getFullYear(), calendarDate.getMonth(), 1);
    const lastDay = new Date(calendarDate.getFullYear(), calendarDate.getMonth() + 1, 0);
    const allWeekArray = [dayList, ...createWeekArray(firstDay, lastDay)];

    setAllDayInfoForRender(allWeekArray);
  }, [calendarDate]);

  return (
    <Wrap>
      <CalendarHead>
        <button type="button" onClick={() => handleCalendarDate('prev')}>
          {'<'}
        </button>
        <Title>
          <div>{MONTH_LIST[calendarDate.getMonth()]}</div>
          <div>{calendarDate.getFullYear()}</div>
        </Title>
        <button type="button" onClick={() => handleCalendarDate('next')}>
          {'>'}
        </button>
      </CalendarHead>
      <DayWrapper>
        {allDayInfoForRender.map((week, i) => week.map((date, j) => <Day key={createKey(date, i + j)}>{date}</Day>))}
      </DayWrapper>
    </Wrap>
  );
}

const createWeekArray = (firstDay, lastDay) => {
  const allWeekArray = [];
  const thisWeekLastDayDate = lastDay.getDate();
  let currentDay = 1;

  let currentWeek = new Array(firstDay.getDay()).fill(null);

  while (currentDay <= thisWeekLastDayDate) {
    if (currentWeek.length < 7) {
      currentWeek.push(currentDay);
    } else {
      allWeekArray.push(currentWeek);
      currentWeek = [currentDay];
    }
    currentDay += 1;
  }
  if (currentWeek.length) {
    allWeekArray.push(currentWeek);
  }
  return allWeekArray;
};
