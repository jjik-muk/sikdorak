import { action, makeObservable, observable, runInAction } from 'mobx';
import { fetchData } from 'utils/fetch';

class UserStore {
  @observable userProfile;

  @observable followStatus: boolean | undefined;

  constructor() {
    makeObservable(this);
  }

  @action
  async fetchUserProfile(id: number) {
    const res = await fetchData({ path: `api/users/${id}` });
    runInAction(() => {
      this.userProfile = res.data;
      this.followStatus = res.data?.relationStatus?.followStatus;
    });
  }

  @action
  async postFollow(id: number) {
    await fetchData({ path: `api/users/follow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    this.followStatus = true;
  }

  @action
  async postUnFollow(id: number) {
    await fetchData({ path: `api/users/unfollow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    this.followStatus = false;
  }
}

export const userStore = new UserStore();
