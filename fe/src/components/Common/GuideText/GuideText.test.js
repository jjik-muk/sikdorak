import { render, screen } from '@testing-library/react';
import GuideText from './GuideText';
import { TEXT } from 'constants/text';

describe('Guide text', () => {
  it('주어진 텍스트를 정상적으로 표시한다.', () => {
    render(<GuideText text={TEXT.NO_REVIEWS} />);
    expect(screen.getByText(TEXT.NO_REVIEWS)).toBeInTheDocument();
  });
});
