import styled from 'styled-components';

function Button({ size, backgroundColor, children, ...rest }: ButtonProps) {
  return <StyledButton {...rest}>{children}</StyledButton>;
}

export default Button;

type ButtonProps = {
  size: 'LARGE' | 'MEDIUM' | 'SMALL';
  backgroundColor: 'WHITE' | 'BLUE';
  children: React.ReactNode;
};

const StyledButton = styled.button`
  width: 'fit-contents';
`;
