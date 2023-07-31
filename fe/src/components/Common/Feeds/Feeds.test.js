import { renderWithProviders } from 'utils/test';
import Feeds from './Feeds';
import { REVIEWS } from 'constants/dummyData';
import { screen } from '@testing-library/react';
import { TEXT } from 'constants/text';

describe('Feeds', () => {
  it('리뷰가 있는 경우 피드를 표시한다.', () => {
    renderWithProviders(<Feeds reviews={REVIEWS} />);
    expect(screen.getByTestId('feed')).toBeInTheDocument();
  });
  it('리뷰가 없는 경우 가이드 텍스트를 표시한다.', () => {
    renderWithProviders(<Feeds reviews={[]} />);
    expect(screen.getByText(TEXT.NO_REVIEWS)).toBeInTheDocument();
  });
});
