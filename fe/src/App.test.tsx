import { render } from '@testing-library/react';
import App from './App';

describe('App', () => {
  it('로그인 컴포넌트 랜더링', () => {
    const { container } = render(<App />);
    expect(container).toHaveTextContent('Kakao 계정으로 로그인');
  });
});
