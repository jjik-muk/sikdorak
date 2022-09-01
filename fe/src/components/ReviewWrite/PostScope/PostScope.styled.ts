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
  padding: 10px;
  ${() => flexLayoutMixin('row', 'space-between', 'center')}
`;

export const ScopeButtonWrapper = styled.div<ScopeButtonWrapperProps>`
  border-radius: 20px;
  border: 2px solid #000;
  background-color: #d0cfce;
  width: 52px;
  height: 26px;
  ${({ scope }) => flexLayoutMixin('row', getScope(scope))};
  cursor: pointer;
`;

export const Circle = styled.div<{ scope: string }>`
  background-color: ${({ scope }) => (scope === 'public' ? 'black' : 'black')};
  width: 50%;
  height: 100%;
  border-radius: 50%;
`;

export const ScopeDescriptionArea = styled.div`
  padding: 10px;
  color: #626262;
`;

const getScope = (scope: string) => scope === 'private' && 'flex-end';

type ScopeButtonWrapperProps = {
  scope: string;
};
