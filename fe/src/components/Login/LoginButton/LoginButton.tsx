import { Wrap } from './LoginButton.styled';

function LoginButton({ text }: { text: string }) {
  return <Wrap text={text}>{text}</Wrap>;
}

export default LoginButton;
