import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const ImgUploadWrapper = styled.div`
  ${() => flexLayoutMixin('column', 'center', 'center')};
  * {
    margin-top: 10px;
  }
  margin: auto auto;
`;

export const ButtonWrapper = styled.button`
  background-color: #7d91d9;
  color: #fff;
  font-size: 14px;
  padding: 10px;
  ${STYLE.BOX_CONTAINER}
  border: 0;
  cursor: pointer;
`;
