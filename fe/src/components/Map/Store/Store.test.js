import { STORE } from 'constants/dummyData';
import { renderWithProviders } from 'utils/test';
import Store from './Store';
import { screen } from '@testing-library/react';

describe('Store', () => {
  beforeEach(() => {
    renderWithProviders(<Store store={STORE} />);
  });
  it('식당 이름을 표시한다.', () => {
    expect(screen.getByText(STORE.storeName)).toBeInTheDocument();
  });
  it('식당 별점을 표시한다.', () => {
    expect(screen.getByText(STORE.reviewScoreAverage)).toBeInTheDocument();
  });
  it('식당 리뷰 수를 표시한다.', () => {
    expect(screen.getByText(`(${STORE.reviewCounts})`)).toBeInTheDocument();
  });
  it('식당 전화번호를 표시한다.', () => {
    expect(screen.getByText(STORE.contactNumber)).toBeInTheDocument();
  });
  it('식당 도로명 주소를 표시한다.', () => {
    expect(screen.getByText(STORE.roadAddressName)).toBeInTheDocument();
  });
});
