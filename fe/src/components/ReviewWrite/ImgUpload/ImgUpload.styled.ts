import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';
import { COLOR } from 'styles/color';

export const ImgUploadWrapper = styled.div`
  ${() => flexLayoutMixin('column', 'center', 'center')};
  * {
    margin-top: 10px;
  }
  margin: auto auto;
`;

export const ButtonWrapper = styled.input`
  display: none;

  ::file-selector-button {
    display: none;
  }
`;

export const Label = styled.label`
  background-color: #7d91d9;
  color: ${COLOR.WHITE};
  font-size: 14px;
  padding: 10px;
  ${STYLE.BOX_CONTAINER}
  border: 0;
  cursor: pointer;
`;
