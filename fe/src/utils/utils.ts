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

export async function fetchData(url: string, { headers, method, bodyData }: any = { header: '', method: 'GET' }) {
  const defaultHeaders = {
    'Content-Type': 'application/json; charset=utf-8',
  };
  const body = JSON.stringify(bodyData);
  const fetchParams = { method, headers: { ...headers, ...defaultHeaders }, body };
  try {
    const data = await fetch(url, fetchParams);
    return await data.json();
  } catch (error) {
    console.error(error);
    return error;
  }
}
