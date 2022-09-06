import { createContext, useContext, useReducer } from 'react';

const INIT_STATE = {
  reviews: [],
};

const reducer = (state, action: ActionType) => {
  switch (action.type) {
    case 'SET_REVIEWS':
      return {
        ...state,
        reviews: action.reviews,
      };
    case 'ADD_REVIEWS':
      return { ...state, reviews: [...state.reviews, ...action.reviews] };
    default:
      return state;
  }
};

export const ReviewsContext: React.Context<{}> = createContext({});
export const DispatchReviewsContext = createContext(undefined);

function ReviewsProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, INIT_STATE);
  return (
    <DispatchReviewsContext.Provider value={dispatch}>
      <ReviewsContext.Provider value={state}>{children}</ReviewsContext.Provider>
    </DispatchReviewsContext.Provider>
  );
}

export const useReviews = () => [useContext(ReviewsContext), useContext(DispatchReviewsContext)];

type ActionType = {
  type: string;
  reviews: object[];
};

export default ReviewsProvider;
