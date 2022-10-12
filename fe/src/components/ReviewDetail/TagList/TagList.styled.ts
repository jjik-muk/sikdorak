import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('')};
  margin-top: 20px;
  align-items: center;
`;

export const Picture = styled.img`
  width: 42px;
  height: 42px;
  border-radius: 50%;
`;

export const CommentWrapper = styled.div`
  width: 100%;
  margin-left: 10px;
  white-space: 'nowrap';
`;

export const ContentWrapper = styled.div`
  ${STYLE.BOX_CONTAINER}
  padding: 10px;
  display: flex;
  gap: 5px;
`;

export const Content = styled.div`
  font-size: 14px;
  font-weight: 400;
  margin-top: 5px;
`;

export const ButtonWrapper = styled.div`
  ${() => flexLayoutMixin()};
  margin-top: 10px;
`;
