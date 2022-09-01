import { DEFAULT_IMG } from 'constants/dummyData';
import { createContext, useContext, useReducer } from 'react';

// const myInfo = JSON.parse(localStorage.getItem('MY_INFO'));

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

export const MyUserInfoContext: React.Context<{}> = createContext({});
export const DispatchMyUserInfoContext = createContext(undefined);

function MyUserInfoProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, INIT_STATE);
  return (
    <DispatchMyUserInfoContext.Provider value={dispatch}>
      <MyUserInfoContext.Provider value={state}>{children}</MyUserInfoContext.Provider>
    </DispatchMyUserInfoContext.Provider>
  );
}

export const useMyUserInfo = () => [useContext(MyUserInfoContext), useContext(DispatchMyUserInfoContext)];

type ActionType = {
  type: string;
  userId: number;
  nickname: string;
  profileImageUrl: string;
};

export default MyUserInfoProvider;
