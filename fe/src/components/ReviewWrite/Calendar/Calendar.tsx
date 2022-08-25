import { DispatchReviewWriteContext } from 'context/reviewWriteProvider';
import { useContext, useEffect, useState } from 'react';
import { CalendarHead, Day, DayWrapper, Title, ToDay, Wrap } from './Calendar.styled';

const MONTH_LIST = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
const dayList = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
const createKey = (value, idx) => `${value}-${idx}`;

export default function Calendar({ setIsCalendarOpen }: CalendarProps) {
  const dispatchCalendar = useContext(DispatchReviewWriteContext);

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

  const clickDay = ({ target }) => {
    const { innerHTML: date, dataset } = target;
    const { year, month, day } = dataset;
    if (Number(date)) {
      dispatchCalendar({ type: 'SET_DATE', date, year, month, day });
      setIsCalendarOpen(false);
    }
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
        {isTodayInMonth(calendarDate, todayDate) &&
          allDayInfoForRender.map((week, i) =>
            week.map((date, j) =>
              isToday(date, todayDate) ? (
                <ToDay
                  data-year={calendarDate.getFullYear()}
                  data-month={calendarDate.getMonth() + 1}
                  data-day={dayList[j]}
                  onClick={clickDay}
                  key={createKey(date, i + j)}
                >
                  {date}
                </ToDay>
              ) : (
                <Day
                  data-year={calendarDate.getFullYear()}
                  data-month={calendarDate.getMonth() + 1}
                  data-day={dayList[j]}
                  onClick={clickDay}
                  day={dayList[j]}
                  key={createKey(date, i + j)}
                >
                  {date}
                </Day>
              ),
            ),
          )}
        {!isTodayInMonth(calendarDate, todayDate) &&
          allDayInfoForRender.map((week, i) =>
            week.map((date, j) => (
              <Day
                data-year={calendarDate.getFullYear()}
                data-month={calendarDate.getMonth() + 1}
                data-day={dayList[j]}
                onClick={clickDay}
                day={dayList[j]}
                key={createKey(date, i + j)}
              >
                {date}
              </Day>
            )),
          )}
      </DayWrapper>
    </Wrap>
  );
}

const isTodayInMonth = (calendarDate: Date, todayDate: Date) => {
  if (calendarDate.getFullYear() !== todayDate.getFullYear()) {
    return false;
  }
  if (calendarDate.getMonth() !== todayDate.getMonth()) {
    return false;
  }
  return true;
};

const isToday = (today: number, todayDate: Date) => today === todayDate.getDate();

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

type CalendarProps = {
  setIsCalendarOpen: Function;
};
