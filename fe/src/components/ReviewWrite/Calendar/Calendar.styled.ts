import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 300px;
  height: 300px;
  margin: 30px;
  padding: 20px;
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
  grid-template-rows: 48px repeat(4, 24px);
  grid-template-columns: repeat(7, 1fr);
  grid-auto-rows: 24px;
  margin-top: 30px;
`;

export const Day = styled.div<{ day: string }>`
  padding: 4px;
  font-size: 14px;
  text-align: center;
  ${({ day }) => getDayColor(day)};
  cursor: pointer;
`;

export const ToDay = styled.div`
  padding: 4px;
  border-radius: 50%;
  background-color: red;
  font-size: 14px;
  text-align: center;
  color: #fff;
  cursor: pointer;
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
