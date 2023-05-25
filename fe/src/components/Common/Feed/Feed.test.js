import { REVIEW } from 'constants/dummyData';
import Feed from './Feed';
import { screen } from '@testing-library/react';
import { renderWithProviders } from 'utils/test';
import { TEST_ID } from 'constants/testID';
import userEvent from '@testing-library/user-event';

describe('Feed', () => {
  it('FeedCard를 표시한다.', () => {
    renderWithProviders(<Feed review={REVIEW} />);
    expect(screen.getByTestId(TEST_ID.FEED_CARD)).toBeInTheDocument();
  });
  it('피드를 클릭하면 리뷰 디테일 모달을 표시한다.', () => {
    renderWithProviders(
      <>
        <Feed review={REVIEW} />
        <div id="portal" />
      </>,
    );
    const feedCard = screen.getByTestId(TEST_ID.FEED_CARD);
    userEvent.click(feedCard);
    expect(screen.getByTestId(TEST_ID.REVIEW_DETAIL)).toBeInTheDocument();
  });
});
