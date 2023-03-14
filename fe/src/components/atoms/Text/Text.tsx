import styled from 'styled-components';

function Text({ size, children, ...rest }: TextProps) {
  return (
    <StyledSpan size={size} {...rest}>
      {children}
    </StyledSpan>
  );
}

const StyledSpan = styled.span<StyledSpanProps>`
  font-size: ${({ size }) => FontSize[size]};
`;

export default Text;

type TextProps = {
  size: 'LARGE' | 'MEDIUM' | 'SMALL';
  label?: string;
  children: React.ReactNode;
};

type StyledSpanProps = Omit<TextProps, 'children'>;

enum FontSize {
  LARGE = '20px',
  MEDIUM = '16px',
  SMALL = '14px',
}
