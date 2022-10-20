import { css } from 'styled-components';

export const flexLayoutMixin = (direction = 'row', justify = 'flex-start', align = 'stretch') => css`
  display: flex;
  flex-direction: ${direction};
  justify-content: ${justify};
  align-items: ${align};
`;

export const createKey = (value: string, idx: number) => `${value}_${idx}`;

export const debounce = (fnc, delay: number = 1000) => {
  let timer;
  return (...args) => {
    clearTimeout(timer);
    timer = setTimeout(() => fnc(...args), delay);
  };
};

export function createErrorMessage(code: string, message: string) {
  return `${code} : ${message}`;
}
