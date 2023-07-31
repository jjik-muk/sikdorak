type RootState = {
  account: AccountStore;
  user: UserStore;
};

type AccountStore = {
  id: number;
  nickname: string;
  profileImage: string;
  accessToken: string;
};

type UserStore = {
  profileImage: string;
  nickname: string;
  reviewCount: number;
  followingCount: number;
  followersCount: number;
  relationStatus: {
    isViewer: boolean;
    followStatus: boolean;
  };
};

export { RootState, AccountStore, UserStore };
