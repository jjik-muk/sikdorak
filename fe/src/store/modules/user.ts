import { fetchData } from 'utils/fetch';
import { RootState, UserStore } from './store';
import { ThunkAction, ThunkDispatch } from 'redux-thunk';

enum UserActions {
  SET_USER_PROFILE = 'SET_USER_PROFILE',
  SET_FOLLOW_STATUS = 'SET_FOLLOW_STATUS',
  RESET_USER_STORE = 'RESET_USER_STORE',
}
type FollowStatus = {
  followStatus: boolean;
};
export type UserAction =
  | { type: UserActions.SET_USER_PROFILE; payload: UserStore }
  | { type: UserActions.SET_FOLLOW_STATUS; payload: FollowStatus }
  | { type: UserActions.RESET_USER_STORE };

function setUserProfile({ profileImage, nickname, reviewCount, followingCount, followersCount, relationStatus }: UserStore): UserAction {
  return {
    type: UserActions.SET_USER_PROFILE,
    payload: {
      profileImage,
      nickname,
      reviewCount,
      followingCount,
      followersCount,
      relationStatus,
    },
  };
}

function setFollowStatus({ followStatus }: FollowStatus): UserAction {
  return {
    type: UserActions.SET_FOLLOW_STATUS,
    payload: {
      followStatus,
    },
  };
}

function resetUserStore(): UserAction {
  return {
    type: UserActions.RESET_USER_STORE,
  };
}

export const initialState: UserStore = {
  profileImage: null,
  nickname: null,
  reviewCount: null,
  followingCount: null,
  followersCount: null,
  relationStatus: {
    isViewer: null,
    followStatus: null,
  },
};

function userReducer(state: UserStore = initialState, action: UserAction): UserStore {
  switch (action.type) {
    case UserActions.SET_USER_PROFILE:
      return {
        ...state,
        profileImage: action.payload.profileImage,
        nickname: action.payload.nickname,
        reviewCount: action.payload.reviewCount,
        followingCount: action.payload.followingCount,
        followersCount: action.payload.followersCount,
        relationStatus: action.payload.relationStatus,
      };
    case UserActions.SET_FOLLOW_STATUS:
      return {
        ...state,
        relationStatus: { ...state.relationStatus, followStatus: action.payload.followStatus },
      };
    case UserActions.RESET_USER_STORE:
      return initialState;
    default:
      return state;
  }
}

type UserThunkAction = ThunkAction<void, RootState, null, UserAction>;
type UserThunkDispatch = ThunkDispatch<RootState, null, UserAction>;

function fetchUserProfile(id: number): UserThunkAction {
  return async (dispatch: UserThunkDispatch) => {
    const res = await fetchData({ path: `api/users/${id}`, withAccessToken: true });
    const { data } = res;
    dispatch(setUserProfile(data));
  };
}

function postFollow(id: number): UserThunkAction {
  return async (dispatch: UserThunkDispatch) => {
    await fetchData({ path: `api/users/follow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    dispatch(setFollowStatus({ followStatus: true }));
  };
}

function postUnfollow(id: number): UserThunkAction {
  return async (dispatch: UserThunkDispatch) => {
    await fetchData({ path: `api/users/unfollow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    dispatch(setFollowStatus({ followStatus: false }));
  };
}

export { setUserProfile, setFollowStatus, resetUserStore, userReducer, fetchUserProfile, postFollow, postUnfollow };
