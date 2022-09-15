import { createContext, useReducer } from 'react';

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
    case 'DELETE_REVIEW':
      return { ...state, reviews: state.reviews.filter((review) => review.reviewId !== action.reviewId) };
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

export default ReviewsProvider;

type ActionType = {
  type: string;
  reviews: object[];
  reviewId: number;
};
