import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 300px;
  height: 300px;
  margin: 30px;
  border: 1px solid #000;
`;

export const CalendarHead = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')};

  button {
    cursor: pointer;
  }
`;

export const Title = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')};
  font-size: 18px;

  div:first-child {
    margin-right: 10px;
  }
`;

export const DayWrapper = styled.div`
  display: grid;
  width: 294px;
  height: 252px;
  grid-template-rows: repeat(4, 42px);
  grid-template-columns: repeat(7, 42px);
  margin-top: 10px;
`;

export const Day = styled.button<{ day: string }>`
  width: 100%;
  height: 100%;
  font-size: 14px;
  ${({ day }) => getDayColor(day)};
  ${flexLayoutMixin('', 'center', 'center')};
  cursor: pointer;
  background-color: white;
`;

const getDayColor = (day) => {
  if (day === 'SAT') {
    return `color:blue`;
  }
  if (day === 'SUN') {
    return `color:red`;
  }
  return '';
};
