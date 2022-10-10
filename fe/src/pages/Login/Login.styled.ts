import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('row', 'center', 'center')}
  height: 100vh;
`;

export const Form = styled.form`
  ${() => flexLayoutMixin('column', 'center', 'center')}
  gap: 32px;

  span {
    color: #4e4b66;
    font-size: 12px;
  }
`;

export const KakaoLogin = styled.a`
  text-decoration: none;
`;
