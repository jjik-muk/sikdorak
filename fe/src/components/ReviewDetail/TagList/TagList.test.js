import { render, screen } from '@testing-library/react';
import TagList from './TagList';

describe('TagList', () => {
  it('전체 태그를 표시한다.', () => {
    render(<TagList tags={['스시', '맛집', '서울']} />);
    expect(screen.getByText('#스시')).toBeInTheDocument();
    expect(screen.getByText('#맛집')).toBeInTheDocument();
    expect(screen.getByText('#서울')).toBeInTheDocument();
  });
});
