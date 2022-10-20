import { API_PATH } from 'constants/apiPath';
import { STATUS_CODE } from 'constants/statusCode';
import { action, makeObservable, observable, runInAction } from 'mobx';
import { fetchData } from 'utils/fetch';
import { openErrorToast } from 'utils/toast';
import { createErrorMessage } from 'utils/utils';

class AccountStore {
  @observable
  accessToken: string;

  @observable
  id: number | null;

  @observable
  nickname: string;

  @observable
  profileImage: string;

  constructor() {
    this.id = null;
    makeObservable(this);
  }

  @action
  async setMyInfo() {
    const myInfo = await fetchData({ path: API_PATH.USER.MY_PROFILE, withAccessToken: true });

    if (!myInfo.data) {
      return;
    }

    runInAction(() => {
      const { id, nickname, profileImage } = myInfo.data;
      this.id = id;
      this.nickname = nickname;
      this.profileImage = profileImage;
    });
  }

  @action
  async setAccessToken(kakaoAuthorizationCode: string) {
    const res = await fetchData({ path: `api/oauth/callback?code=${kakaoAuthorizationCode}`, customHeaders: { credentials: 'include' } });
    const { code, data, message } = res;

    if (code === STATUS_CODE.FAILURE.COMMUNICATION_WITH_OAUTH_SERVER) {
      const errorMessage = createErrorMessage(code, message);
      openErrorToast(errorMessage);
      throw new Error(errorMessage);
    }
    this.updateAccessToken(data.accessToken);
  }

  @action
  async reissueAccessToken() {
    const res = await fetchData({ path: API_PATH.LOGIN.REISSUE_ACCESS_TOKEN, customHeaders: { credentials: 'include' } });
    const resJson = await res.json();
    this.updateAccessToken(resJson.data.accessToken);
  }

  @action
  updateAccessToken(newAccessToken: string) {
    runInAction(() => {
      this.accessToken = newAccessToken;
    });
    localStorage.setItem('accessToken', newAccessToken);
  }
}

export const accountStore = new AccountStore();
