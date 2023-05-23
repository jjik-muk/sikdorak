import { render, screen } from '@testing-library/react';
import Loading from './Loading';

describe('Loading', () => {
  it('로딩 스피너를 표시한다.', () => {
    render(<Loading />);
    expect(screen.getByTestId('spinner-wrap')).toBeInTheDocument();
  });
});
