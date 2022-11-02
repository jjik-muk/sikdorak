import { API_PATH } from 'constants/apiPath';
import { STATUS_CODE } from 'constants/statusCode';

const BASE_URL = process.env.REACT_APP_BE_SERVER_URL;

// TODO: 더 나은 이름 고민해보기
export async function fetchData({ path, method = 'GET', customHeaders = {}, bodyData, withAccessToken = false }: FetchDataParams) {
  const accessToken = localStorage.getItem('accessToken');
  const defaultHeaders = {
    contentType: { 'Content-Type': 'application/json; charset=utf-8' },
    authorization: { Authorization: `Bearer ${accessToken}` },
  };
  const { contentType, authorization } = defaultHeaders;
  const headers = Object.assign({}, contentType, withAccessToken && authorization, customHeaders);
  const body = JSON.stringify(bodyData);
  const res = await fetch(`${BASE_URL}/${path}`, { method, headers, body });
  const resJson = await res.json();
  if (resJson.code === STATUS_CODE.FAILURE.EXPIRED_ACCESS_TOKEN) {
    localStorage.removeItem('accessToken');
  }
  return resJson;
}

type FetchDataParams = {
  path: string;
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
  customHeaders?: object;
  bodyData?: object;
  withAccessToken?: boolean;
};

export async function validateAccessToken() {
  const { code } = await fetchData({ path: API_PATH.USER.MY_PROFILE, withAccessToken: true });
  return code === STATUS_CODE.SUCCESS.MY_USER_PROFILE;
}
