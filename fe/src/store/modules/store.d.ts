type RootState = {
  account: accountStore;
};

type AccountStore = {
  id: number;
  nickname: string;
  profileImage: string;
  accessToken: string;
};

export { RootState, AccountStore };
