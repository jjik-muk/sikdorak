import { render, screen } from '@testing-library/react';
import Rating from './Rating';
import { TEST_ID } from 'constants/testID';

describe('Rating', () => {
  it('별점이 0점이면 회색 별 5개를 표시한다.', () => {
    render(<Rating rating={0} />);
    const greyStars = screen.getAllByTestId(TEST_ID.GREY_STAR);
    expect(greyStars.length).toBe(5);
  });
  it('별점이 3점이면 노란색 별 3개, 회식 별 2개를 표시한다.', () => {
    render(<Rating rating={3} />);
    const yellowStars = screen.getAllByTestId(TEST_ID.YELLOW_STAR);
    const greyStars = screen.getAllByTestId(TEST_ID.GREY_STAR);
    expect(yellowStars.length).toBe(3);
    expect(greyStars.length).toBe(2);
  });
  it('별점이 5점이면 노란색 별 5개를 표시한다.', () => {
    render(<Rating rating={5} />);
    const yellowStars = screen.getAllByTestId(TEST_ID.YELLOW_STAR);
    expect(yellowStars.length).toBe(5);
  });
});
