import { TEXT } from 'constants/text';
import { Wrap } from './LoginInput.styled';
import { MESSAGE } from 'constants/message';
import { TEST_ID } from 'constants/testID';

function LoginInput({ type }: { type: string }) {
  return <Wrap data-testid={TEST_ID.LOGIN_INPUT} type={getLoginInputType(type)} placeholder={type} />;
}

function getLoginInputType(type: string) {
  if (type === TEXT.LOGIN_INPUT.ID) return 'text';
  if (type === TEXT.LOGIN_INPUT.PASSWORD) return 'password';

  throw new Error(MESSAGE.ERROR.NO_VALID_TYPE_OF_LOGIN_INPUT);
}

export default LoginInput;
