import TEXT from 'constants/text';
import { render, screen } from '@testing-library/react';
import LoginInput from './LoginInput';

const { ID, PASSWORD } = TEXT.INPUT;

describe('Login input', () => {
  it('아이디', () => {
    render(<LoginInput type={ID} />);
    expect(screen.getByTestId('login-input')).toHaveAttribute('placeholder', '아이디');
  });

  it('패스워드', () => {
    render(<LoginInput type={PASSWORD} />);
    expect(screen.getByTestId('login-input')).toHaveAttribute('placeholder', '비밀번호');
  });
});
