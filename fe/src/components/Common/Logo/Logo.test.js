import { screen } from '@testing-library/react';
import Logo from './Logo';
import { ALT } from 'constants/alt';
import { renderWithProviders } from 'utils/test';
describe('Loading', () => {
  it('로고를 표시한다.', () => {
    renderWithProviders(<Logo />);
    expect(screen.getByAltText(ALT.LOGO)).toBeInTheDocument();
  });
});
