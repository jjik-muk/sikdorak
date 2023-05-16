import { fetchData } from 'utils/fetch';
import { UserStore } from './store';

enum UserActionTypes {
  SET_USER_PROFILE = 'SET_USER_PROFILE',
  SET_FOLLOW_STATUS = 'SET_FOLLOW_STATUS',
}
type FollowStatus = {
  followStatus: boolean;
};
export type UserAction = { type: UserActionTypes.SET_USER_PROFILE; payload: UserStore } | { type: UserActionTypes.SET_FOLLOW_STATUS; payload: FollowStatus };
const SET_USER_PROFILE = 'SET_USER_PROFILE';
const SET_FOLLOW_STATUS = 'SET_FOLLOW_STATUS';

function setUserProfile({ profileImage, nickname, reviewCount, followingCount, followersCount, relationStatus }: UserStore) {
  return {
    type: SET_USER_PROFILE,
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

function setFollowStatus({ followStatus }: FollowStatus) {
  return {
    type: SET_FOLLOW_STATUS,
    payload: {
      followStatus,
    },
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

function userReducer(state: UserStore = initialState, action: UserAction) {
  switch (action.type) {
    case SET_USER_PROFILE:
      return {
        ...state,
        profileImage: action.payload.profileImage,
        nickname: action.payload.nickname,
        reviewCount: action.payload.reviewCount,
        followingCount: action.payload.followingCount,
        followersCount: action.payload.followersCount,
        relationStatus: action.payload.relationStatus,
      };
    case SET_FOLLOW_STATUS:
      return {
        ...state,
        relationStatus: { ...state.relationStatus, followStatus: action.payload.followStatus },
      };
    default:
      return state;
  }
}

function fetchUserProfile(id: number) {
  return async (dispatch) => {
    const res = await fetchData({ path: `api/users/${id}`, withAccessToken: true });
    const { data } = res;
    dispatch(setUserProfile(data));
  };
}

function postFollow(id: number) {
  return async (dispatch) => {
    await fetchData({ path: `api/users/follow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    // TODO: 팔로우 실패시 분기 처리
    // const { message, code, data } = res;
    dispatch(setFollowStatus({ followStatus: true }));
  };
}

function postUnfollow(id: number) {
  return async (dispatch) => {
    await fetchData({ path: `api/users/unfollow`, method: 'PUT', bodyData: { userId: id }, withAccessToken: true });
    // const { message, code, data } = res;
    dispatch(setFollowStatus({ followStatus: false }));
  };
}

export { setUserProfile, setFollowStatus, userReducer, fetchUserProfile, postFollow, postUnfollow };
