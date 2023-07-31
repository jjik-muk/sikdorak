import { TEXT } from 'constants/text';
import LoginInput from './LoginInput';
import { render, screen } from '@testing-library/react';
import { MESSAGE } from 'constants/message';

describe('Login input', () => {
  it('아이디 입력창을 표시한다.', () => {
    render(<LoginInput type={TEXT.LOGIN_INPUT.ID} />);
    expect(screen.getByPlaceholderText(TEXT.LOGIN_INPUT.ID));
  });
  it('비밀번호 입력창을 표시한다.', () => {
    render(<LoginInput type={TEXT.LOGIN_INPUT.PASSWORD} />);
    expect(screen.getByPlaceholderText(TEXT.LOGIN_INPUT.PASSWORD));
  });
  it('type에 로그인, 패스워드 이외의 값을 삽입하면 에러를 던진다.', () => {
    expect(() => render(<LoginInput type="nonValidType" />)).toThrowError(MESSAGE.ERROR.NO_VALID_TYPE_OF_LOGIN_INPUT);
  });
});
