import { render, screen } from '@testing-library/react';
import LoginButton from './LoginButton';
import { TEXT } from 'constants/text';
import userEvent from '@testing-library/user-event';

describe('Login button', () => {
  it('카카오 로그인 버튼을 표시한다.', () => {
    render(<LoginButton text={TEXT.LOGIN_BTN.KAKAO} />);
    expect(screen.getByText(TEXT.LOGIN_BTN.KAKAO)).toBeInTheDocument();
  });
  it('버튼을 클릭하면 onClick prop으로 전달한 함수를 실행한다.', () => {
    const handleClick = jest.fn();
    render(<LoginButton text={TEXT.LOGIN_BTN.KAKAO} onClick={handleClick} />);
    userEvent.click(screen.getByText(TEXT.LOGIN_BTN.KAKAO));
    expect(handleClick).toBeCalled();
  });
});
