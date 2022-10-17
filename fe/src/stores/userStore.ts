import { action, makeObservable, observable, runInAction } from 'mobx';
import { fetchData, fetchDataThatNeedToLogin } from 'utils/utils';

class UserStore {
  @observable userProfile;

  @observable followStatus: boolean | undefined;

  constructor() {
    makeObservable(this);
  }

  @action
  async fetchUserProfile(id: number) {
    const res = await fetchData(`${process.env.REACT_APP_BE_SERVER_URL}/api/users/${id}`);
    runInAction(() => {
      this.userProfile = res.data;
      this.followStatus = res.data?.relationStatus?.followStatus;
    });
  }

  @action
  async postFollow(id: number) {
    await fetchDataThatNeedToLogin(`api/users/follow`, {
      method: 'PUT',
      bodyData: { userId: id },
    });
    this.followStatus = true;
  }

  @action
  async postUnFollow(id: number) {
    await fetchDataThatNeedToLogin(`api/users/unfollow`, {
      method: 'PUT',
      bodyData: { userId: id },
    });
    this.followStatus = false;
  }
}

export const userStore = new UserStore();
