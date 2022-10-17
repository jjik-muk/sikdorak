import { STATUS_CODE } from 'constants/statusCode';
import { action, makeObservable, observable, runInAction } from 'mobx';
import { fetchDataThatNeedToLogin } from 'utils/utils';

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
    const myInfo = await fetchDataThatNeedToLogin(`api/users/me`);

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
  async setAccessToken(kakaoAuthorizationCode) {
    const res = await fetch(`${process.env.REACT_APP_BE_SERVER_URL}/api/oauth/callback?code=${kakaoAuthorizationCode}`, {
      credentials: 'include',
    });
    const resJson = await res.json();
    const { code, data } = resJson;

    if (code === STATUS_CODE.FAIL_COMMUNICATION_WITH_OAUTH_SERVER) {
      alert('OAuth 서버와의 통신이 원할하지 않습니다.');
      throw new Error('OAuth 서버와의 통신이 원할하지 않습니다.');
    }

    this.accessToken = data.accessToken;
    localStorage.setItem('accessToken', this.accessToken);
  }
}

export const accountStore = new AccountStore();
