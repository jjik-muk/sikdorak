import { render, screen } from '@testing-library/react';
import PostScope from './PostScope';

describe('RestaurantSearch', () => {
  it('게시글 공개범위 컴포넌트는 게시물 공개 범위 텍스트를 포함한다.', () => {
    render(<PostScope />);
    screen.getByText('게시물 공개 범위');
  });
});
