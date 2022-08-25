import { Wrap } from './LoginButton.styled';

function LoginButton({ text, onClick }: { text: string; onClick?: () => void }) {
  return (
    <Wrap text={text} onClick={onClick}>
      {text}
    </Wrap>
  );
}

export default LoginButton;
