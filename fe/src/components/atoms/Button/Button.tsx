import styled from 'styled-components';
import { COLOR } from '../../../styles/color';

function Button({ size = 'SMALL', backgroundColor = 'BLUE', children, ...rest }: ButtonProps) {
  return (
    <StyledButton size={size} backgroundColor={backgroundColor} {...rest}>
      {children}
    </StyledButton>
  );
}

export default Button;

type ButtonProps = {
  size?: 'LARGE' | 'SMALL';
  backgroundColor?: 'YELLOW' | 'BLUE';
  children: React.ReactNode;
  label?: string;
};

const StyledButton = styled.button<ButtonProps>`
  width: 'fit-contents';
  border: 0;

  background-color: ${({ backgroundColor }) => COLOR[backgroundColor]};
  color: ${({ backgroundColor }) => (backgroundColor === 'BLUE' ? COLOR.WHITE : COLOR.BLACK)};
  padding: ${({ size }) => (size === 'LARGE' ? '20px 80px' : '10px 15px')};
`;
