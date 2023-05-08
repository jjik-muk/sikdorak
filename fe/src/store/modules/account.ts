import { API_PATH } from 'constants/apiPath';
import { STATUS_CODE } from 'constants/statusCode';
import { fetchData } from 'utils/fetch';
import { openErrorToast } from 'utils/toast';
import { createErrorMessage } from 'utils/utils';
import { AccountStore, RootState } from './store';
import { ThunkAction, ThunkDispatch } from 'redux-thunk';

enum AccountActionTypes {
  SET_MY_INFO = 'SET_MY_INFO',
  SET_ACCESS_TOKEN = 'SET_ACCESS_TOKEN',
}
type MyInfo = Pick<AccountStore, 'id' | 'nickname' | 'profileImage'>;
type AccessToken = Pick<AccountStore, 'accessToken'>;
export type AccountAction = { type: AccountActionTypes.SET_MY_INFO; payload: MyInfo } | { type: AccountActionTypes.SET_ACCESS_TOKEN; payload: AccessToken };
type KakaoAuthCode = string;

function setMyInfo({ id, nickname, profileImage }: MyInfo): AccountAction {
  return {
    type: AccountActionTypes.SET_MY_INFO,
    payload: {
      id,
      nickname,
      profileImage,
    },
  };
}

function setAccessToken(accessToken: string): AccountAction {
  return {
    type: AccountActionTypes.SET_ACCESS_TOKEN,
    payload: {
      accessToken,
    },
  };
}

const initialState: AccountStore = {
  id: null,
  nickname: null,
  profileImage: null,
  accessToken: null,
};

function accountReducer(state: AccountStore = initialState, action: AccountAction) {
  switch (action.type) {
    case AccountActionTypes.SET_MY_INFO:
      return {
        ...state,
        id: action.payload.id,
        nickname: action.payload.nickname,
        profileImage: action.payload.profileImage,
      };
    case AccountActionTypes.SET_ACCESS_TOKEN:
      return {
        ...state,
        accessToken: action.payload.accessToken,
      };
    default:
      return state;
  }
}

function fetchAccessToken(kakaoAuthCode: KakaoAuthCode): ThunkAction<void, RootState, null, AccountAction> {
  return async (dispatch: ThunkDispatch<RootState, null, AccountAction>) => {
    const res = await fetchData({ path: `api/oauth/kakao/callback?code=${kakaoAuthCode}`, customHeaders: { credentials: 'include' } });
    const { code, data, message } = res;
    if (code === STATUS_CODE.FAILURE.COMMUNICATION_WITH_OAUTH_SERVER) {
      const errorMessage = createErrorMessage(code, message);
      openErrorToast(errorMessage);
      throw new Error(errorMessage);
    }
    dispatch(setAccessToken(data.accessToken));
    localStorage.setItem('accessToken', data.accessToken);
  };
}

function fetchMyInfo(): ThunkAction<void, RootState, null, AccountAction> {
  return async (dispatch: ThunkDispatch<RootState, null, AccountAction>) => {
    const myInfo = await fetchData({ path: API_PATH.USER.MY_PROFILE, withAccessToken: true });
    if (!myInfo.data) return;
    const { id, nickname, profileImage } = myInfo.data;
    dispatch(setMyInfo({ id, nickname, profileImage }));
  };
}

export { setMyInfo, setAccessToken, accountReducer, fetchAccessToken, fetchMyInfo };
