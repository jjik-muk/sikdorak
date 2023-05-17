import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import store from 'store/modules';
import { ReactNode } from 'react';

export function renderWithProviders(children: ReactNode) {
  return render(
    <Provider store={store}>
      <MemoryRouter>{children}</MemoryRouter>
    </Provider>,
  );
}
