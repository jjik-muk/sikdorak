import { screen, waitFor } from '@testing-library/react';
import { login, renderWithProviders } from 'utils/test';
import Comment from './Comment';
import userEvent from '@testing-library/user-event';

describe('Comment', () => {
  it('댓글 작성자 이름을 표시한다.', () => {
    renderWithProviders(<Comment title="럼카" />);
    expect(screen.getByText('럼카')).toBeInTheDocument();
  });
  it('댓글 내용을 표시한다.', () => {
    renderWithProviders(<Comment content="댓글 내용" />);
    expect(screen.getByText('댓글 내용')).toBeInTheDocument();
  });
  it('내가 작성한 리뷰일 경우 수정, 삭제 버튼을 표시한다.', () => {
    login({ id: 1, nickname: '럼카' });
    renderWithProviders(<Comment authorId={1} />);
    expect(screen.getByText('수정')).toBeInTheDocument();
    expect(screen.getByText('삭제')).toBeInTheDocument();
  });
  it('수정 버튼을 클릭하면 수정폼을 표시한다.', async () => {
    login({ id: 1, nickname: '럼카' });
    renderWithProviders(<Comment authorId={1} />);
    userEvent.click(screen.getByText('수정'));
    await waitFor(() => {
      expect(screen.getByText('완료'));
      expect(screen.getByText('취소'));
    });
  });
});
