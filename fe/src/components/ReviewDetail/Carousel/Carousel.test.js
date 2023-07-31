import { render, screen } from '@testing-library/react';
import Carousel from './Carousel';
import { URLS } from 'constants/dummyData';
import { TEST_ID } from 'constants/testID';
import userEvent from '@testing-library/user-event';

describe('Carousel', () => {
  // TODO: 다음 || 이전 버튼을 클릭하면 이전 || 다음 사진을 표시한다 테스트 추가
  it('사진을 넘기는 도중에는 버튼을 disabled 한다.', () => {
    render(<Carousel urls={URLS} />);
    const nextBtn = screen.getByTestId(TEST_ID.NEXT_BTN);
    userEvent.click(nextBtn);
    expect(nextBtn).toHaveProperty('disabled');
  });
});
