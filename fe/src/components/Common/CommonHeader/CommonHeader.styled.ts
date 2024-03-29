import { STYLE } from 'styles/style';
import { Z_INDEX } from 'styles/zIndex';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';
import { COLOR } from 'styles/color';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')};
  position: sticky;
  top: 0;
  padding: 10px 0 10px 0;
  background-color: ${COLOR.WHITE};
  z-index: ${Z_INDEX.COMMON_HEADER};
`;

export const Header = styled.div`
  ${() => flexLayoutMixin('', 'space-between', 'center')}
  width:1000px;
  padding: 10px 20px;
`;

export const SearchFormWrap = styled.form`
  position: relative;
`;

export const Input = styled.input`
  width: 300px;
  height: 30px;
  padding: 7px;
  ${STYLE.BOX_CONTAINER}
  resize: none;
  overflow: hidden;
`;

export const IconWrap = styled.div`
  position: absolute;
  right: 7px;
  top: 7px;
  cursor: pointer;
`;

export const ButtonWrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')}
  gap:23px;
  * {
    cursor: pointer;
  }
`;

export const ProfileImageWrap = styled.div``;
