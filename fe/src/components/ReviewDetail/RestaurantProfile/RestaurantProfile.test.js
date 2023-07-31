import { renderWithProviders } from 'utils/test';
import RestaurantProfile from './RestaurantProfile';
import { screen } from '@testing-library/react';

describe('RestaurantProfile', () => {
  it('식당 이름을 표시한다.', () => {
    renderWithProviders(<RestaurantProfile company="모구" />);
    expect(screen.getByText('모구')).toBeInTheDocument();
  });
});
