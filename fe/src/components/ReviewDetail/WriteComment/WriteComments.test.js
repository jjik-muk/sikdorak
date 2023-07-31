import { fireEvent, screen, waitFor } from '@testing-library/react';
import WriteComment from './WriteComment';
import userEvent from '@testing-library/user-event';
import { login, pressEnter, renderWithProviders } from 'utils/test';
import { ToastContainer } from 'react-toastify';
import { MESSAGE } from 'constants/message';

describe('Write comments', () => {
  it('댓글 내용이 빈 채로 엔터를 누르면 경고 메세지를 표시한다.', () => {
    renderWithProviders(
      <>
        <WriteComment />
        <ToastContainer />
      </>,
    );
    const commentInput = screen.getByTestId('comment-input').childNodes[1].childNodes[0];
    pressEnter(commentInput);
    waitFor(() => {
      expect(screen.getByText(MESSAGE.ERROR.ENTER_YOUR_COMMENT)).toBeInTheDocument();
    });
  });
  it('로그인하지 않고 댓글 작성 및 엔터를 누르면 경고 메세지를 표시한다.', () => {
    renderWithProviders(
      <>
        <WriteComment />
        <ToastContainer />
      </>,
    );
    const commentInput = screen.getByTestId('comment-input').childNodes[1].childNodes[0];
    userEvent.type(commentInput, '댓글 내용');
    pressEnter(commentInput);
    waitFor(() => {
      expect(screen.getByText(MESSAGE.ERROR.NEED_LOGIN)).toBeInTheDocument();
    });
  });
  it('로그인하고 댓글 작성 및 엔터를 누르면 fetchAndSetComments를 호출한다.', () => {
    const mockFetchAndSetComments = jest.fn();
    login({ id: 1, nickname: '럼카' });
    renderWithProviders(
      <>
        <WriteComment fetchAndSetComments={mockFetchAndSetComments} />
        <ToastContainer />
      </>,
    );
    const commentInput = screen.getByTestId('comment-input').childNodes[1].childNodes[0];
    userEvent.type(commentInput, '댓글 내용');
    pressEnter(commentInput);
    waitFor(() => {
      expect(mockFetchAndSetComments).toBeCalled();
    });
  });
});
