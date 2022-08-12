import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 100%;
  padding: 12px;
`;

export const PostScopeWrapper = styled.div`
  ${() => flexLayoutMixin('row', 'space-between', 'center')};
  width: 100%;
  font-size: 15px;
  border-color: #d4d4d4;
  :focus {
    outline: none;
  }
`;
export const ScopeWrapper = styled.div`
  ${() => flexLayoutMixin('row', 'space-between', 'center')}
`;

export const ScopeButtonWrapper = styled.div<ScopeButtonWrapperProps>`
  border-radius: 20px;
  border: 2px solid #000;
  background-color: #d0cfce;
  width: 60px;
  height: 24px;
  ${({ scope }) => flexLayoutMixin('row', getScope(scope))}
`;

export const Circle = styled.div`
  background-color: red;
  width: 33%;
  height: 100%;
  border-radius: 50%;
`;

export const ScopeDescriptionArea = styled.div`
  color: #626262;
`;

const getScope = (scope: string) => scope === 'friends' && 'flex-end';

interface ScopeButtonWrapperProps {
  scope: string;
}
