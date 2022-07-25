import TEXT from 'constants/text';
import { render } from '@testing-library/react';
import LoginButton from './LoginButton';

describe('LoginButton', () => {
  const { KAKAO, NORMAL } = TEXT.LOGIN_BTN;

  it('카카오 버튼 테스트', () => {
    const { container } = render(<LoginButton text={KAKAO} />);
    expect(container).toHaveTextContent('Kakao 계정으로 로그인');
  });

  it('식도락 로그인 버튼 테스트', () => {
    const { container } = render(<LoginButton text={NORMAL} />);
    expect(container).toHaveTextContent('아이디로 로그인');
  });
});
