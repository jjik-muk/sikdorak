import { render } from '@testing-library/react';
import Profile from './UserProfile';

describe('Profile', () => {
  it('랜더 잘 되니?', () => {
    const { container } = render(<Profile nickname="호이" />);
    expect(container).toHaveTextContent('호이');
  });
});
