import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')};
  position: sticky;
  top: 0;
  padding: 10px 0 10px 0;
  background-color: #fff;
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
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  border: 1px solid #fefefe;
  resize: none;
  overflow: hidden;
`;

export const IconWrap = styled.div`
  position: absolute;
  right: 7px;
  top: 7px;
`;

export const ButtonWrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')}
  gap:23px;
  * {
    cursor: pointer;
  }
`;
