import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('')};
  margin-top: 20px;
`;

export const Picture = styled.img`
  width: 42px;
  height: 42px;
  border-radius: 50%;
  cursor: pointer;
`;

export const CommentWrapper = styled.div`
  width: 100%;
  margin-left: 10px;
`;

export const ContentWrapper = styled.div`
  ${STYLE.BOX_CONTAINER}
  padding: 10px;
  padding-top: 0;
`;

export const Title = styled.div`
  font-size: 18px;
  font-weight: 500;
  margin-top: 5px;
  cursor: pointer;
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

export const Button = styled.button`
  all: unset;
  cursor: pointer;
  :focus {
    outline: orange 5px auto;
  }
  margin-left: 20px;
`;
