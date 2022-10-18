import { STATUS_CODE } from 'constants/statusCode';
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
    return new Error(error);
  }
}

export async function fetchDataThatNeedToLogin(path: string, { headers, method, bodyData }: any = { header: '', method: 'GET' }) {
  const accessToken = localStorage.getItem('accessToken');
  const defaultHeaders = {
    'Content-Type': 'application/json; charset=utf-8',
    Authorization: `Bearer ${accessToken}`,
  };
  const body = JSON.stringify(bodyData);
  const fetchParams = { method, headers: { ...headers, ...defaultHeaders }, body };

  const res = await fetch(`${process.env.REACT_APP_BE_SERVER_URL}/${path}`, fetchParams);
  const resJson = await res.json();

  if (resJson.code === STATUS_CODE.FAILURE.EXPIRED_ACCESS_TOKEN) {
    reissueAccessToken();
  }
  return resJson;

  async function reissueAccessToken() {
    const refreshRes = await fetch(`api/oauth/refresh`, { credentials: 'include' });
    const refreshResJson = await refreshRes.json();
    localStorage.setItem('accessToken', refreshResJson.data.accessToken);
  }
}

export function createErrorMessage(code: string, message: string) {
  return `${code} : ${message}`;
}
