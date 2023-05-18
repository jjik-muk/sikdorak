import { screen, waitFor } from '@testing-library/react';
import FollowButton from './FollowButton';
import { resetUserStore, setFollowStatus } from 'store/modules/user';
import store from 'store/modules';
import { ToastContainer } from 'react-toastify';
import userEvent from '@testing-library/user-event';
import { renderWithProviders } from 'utils/test';
import { MESSAGE } from 'constants/message';

describe('팔로우 버튼', () => {
  beforeEach(() => {
    store.dispatch(resetUserStore());
  });
  it('팔로우 하지 않은 유저의 프로필을 방문한 경우 팔로우 버튼을 표시한다.', () => {
    store.dispatch(setFollowStatus({ followStatus: false }));
    renderWithProviders(<FollowButton />);
    expect(screen.getByText('팔로우')).toBeInTheDocument();
  });
  it('팔로우한 유저의 프로필을 방문한 경우 언팔로우 버튼을 표시한다.', () => {
    store.dispatch(setFollowStatus({ followStatus: true }));
    renderWithProviders(<FollowButton />);
    expect(screen.getByText('언팔로우'));
  });
  it('로그인이 안된 상태로 팔로우 버튼을 클릭하면 로그인 유도 문구를 표시한다.', async () => {
    renderWithProviders(
      <>
        <FollowButton />
        <ToastContainer />
      </>,
    );
    userEvent.click(screen.getByText('팔로우'));
    await waitFor(() => {
      expect(screen.getByText(MESSAGE.ERROR.NEED_LOGIN)).toBeInTheDocument();
    });
  });
});
