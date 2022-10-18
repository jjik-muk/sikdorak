import { TEXT } from 'constants/text';
import { Wrap } from './LoginInput.styled';

const { ID, PASSWORD } = TEXT.INPUT;

function LoginInput({ type }: { type: string }) {
  return <Wrap data-testid="login-input" type={getLoginInputType(type)} placeholder={type} />;
}

function getLoginInputType(type: string) {
  if (type === ID) return 'text';
  if (type === PASSWORD) return 'password';

  throw new Error('로그인 인풋 타입이 올바르지 않습니다.');
}

export default LoginInput;
