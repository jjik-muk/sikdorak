import { render, screen } from '@testing-library/react';
import KebabMenu from './KebabMenu';
import userEvent from '@testing-library/user-event';

describe('Kebab menu', () => {
  it('자신이 작성한 리뷰가 아니면 케밥 메뉴를 표시하지 않는다.', () => {
    render(<KebabMenu reviewId={1} />);
    const kebabMenu = screen.queryByTestId('kebab-menu');
    expect(kebabMenu).not.toBeInTheDocument();
  });
  it('케밥 메뉴를 클릭하면 수정, 삭제, 취소 버튼을 표시한다.', () => {
    render(<KebabMenu reviewId={1} isMyFeed />);
    const kebabMenu = screen.getByTestId('kebab-menu');
    userEvent.click(kebabMenu);
    expect(screen.getByText('수정')).toBeInTheDocument();
    expect(screen.getByText('삭제')).toBeInTheDocument();
    expect(screen.getByText('취소')).toBeInTheDocument();
  });
  it('케밥 메뉴 클릭 후 액션 버튼 이외의 영역을 클릭하면 액션 버튼을 표시하지 않는다.', () => {
    render(
      <>
        <KebabMenu reviewId={1} isMyFeed />
        <div data-testid="other-area"></div>
      </>,
    );
    const kebabMenu = screen.getByTestId('kebab-menu');
    const otherArea = screen.getByTestId('other-area');
    userEvent.click(kebabMenu);
    userEvent.click(otherArea);
    expect(screen.queryByText('수정')).not.toBeInTheDocument();
    expect(screen.queryByText('삭제')).not.toBeInTheDocument();
    expect(screen.queryByText('취소')).not.toBeInTheDocument();
  });
});
