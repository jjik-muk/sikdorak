import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const ImgUploadWrapper = styled.div`
  ${() => flexLayoutMixin('column', 'center', 'center')};
  * {
    margin-top: 10px;
  }
`;
