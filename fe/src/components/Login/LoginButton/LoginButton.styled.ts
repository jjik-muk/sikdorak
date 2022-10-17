import { TEXT } from 'constants/text';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

const { KAKAO } = TEXT.LOGIN_BTN;
const getBackgroundColor = (text: string) => `background-color: ${text === KAKAO ? '#FAE100' : '#007AFF'}`;
const getColor = (text: string) => `color: ${text === KAKAO ? '#000' : '#fefefe'}`;

export const Wrap = styled.div<{ text: string }>`
  ${({ text }) => `
    ${getBackgroundColor(text)};
    ${getColor(text)}
  `};
  ${() => flexLayoutMixin('row', 'center', 'center')};
  font-size: 18px;
  font-weight: 600;
  width: 340px;
  height: 64px;
  border-radius: 20px;
`;
