import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import store from 'store/modules';
import { ReactNode } from 'react';
import { setMyInfo } from 'store/modules/account';

export function renderWithProviders(children: ReactNode) {
  return render(
    <Provider store={store}>
      <MemoryRouter>{children}</MemoryRouter>
    </Provider>,
  );
}

export function login({ id, nickname }) {
  return store.dispatch(setMyInfo({ id, nickname, profileImage: 'Image URL' }));
}
