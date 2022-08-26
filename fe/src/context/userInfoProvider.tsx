import { DEFAULT_IMG } from 'constants/dummyData';
import { createContext, useContext, useReducer } from 'react';

const INIT_STATE = {
  userId: 0,
  nickname: '',
  profileImageUrl: DEFAULT_IMG,
};

const reducer = (state, action: ActionType) => {
  const { type, userId, nickname, profileImageUrl } = action;
  switch (type) {
    case 'SET_USER':
      return {
        ...state,
        userId,
        nickname,
        profileImageUrl,
      };
    default:
      return state;
  }
};

export const UserInfoContext: React.Context<{}> = createContext({});
export const DispatchUserInfoContext = createContext(undefined);

function UserInfoProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, INIT_STATE);
  return (
    <DispatchUserInfoContext.Provider value={dispatch}>
      <UserInfoContext.Provider value={state}>{children}</UserInfoContext.Provider>
    </DispatchUserInfoContext.Provider>
  );
}

export const useUserInfo = () => [useContext(UserInfoContext), useContext(DispatchUserInfoContext)];

type ActionType = {
  type: string;
  userId: number;
  nickname: string;
  profileImageUrl: string;
};

export default UserInfoProvider;
