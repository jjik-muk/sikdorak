import { createContext, useContext, useReducer } from 'react';

const INIT_STATE = {
  userId: 0,
};

const reducer = (state, action: ActionType) => {
  const { type, userId } = action;
  switch (type) {
    case 'SET_USER':
      return {
        ...state,
        userId,
      };
    default:
      return state;
  }
};

export const ReviewDetailContext: React.Context<{}> = createContext({});
export const DispatchReviewDetailContext = createContext(undefined);

function ReviewDetailProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, INIT_STATE);
  return (
    <DispatchReviewDetailContext.Provider value={dispatch}>
      <ReviewDetailContext.Provider value={state}>{children}</ReviewDetailContext.Provider>
    </DispatchReviewDetailContext.Provider>
  );
}

export const useReviewDetail = () => [useContext(ReviewDetailContext), useContext(DispatchReviewDetailContext)];

type ActionType = {
  type: string;
  userId: number;
};

export default ReviewDetailProvider;
