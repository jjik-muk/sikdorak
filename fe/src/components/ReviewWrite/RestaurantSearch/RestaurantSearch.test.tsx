import { render, screen } from '@testing-library/react';
import RestaurantSearch from './RestaurantSearch';

describe('RestaurantSearch', () => {
  it('식당 검색 컴포넌트 placeholder에는 식당을 검색해주세요. 텍스트가 입력되어있다.', () => {
    render(<RestaurantSearch />);
    screen.getByPlaceholderText('식당을 검색해주세요.');
  });
});
