import { createContext, useContext, useReducer } from 'react';

const initState = {
  year: 0,
  month: 0,
  date: 0,
  day: '',
  restaurant: '',
  content: '',
  rating: 0,
  tags: [],
  scope: 'public',
  id: 0,
  address: '',
};

const reducer = (state, action: ActionType) => {
  switch (action.type) {
    case 'SET_DATE':
      return {
        ...state,
        year: action.year,
        month: action.month,
        date: action.date,
        day: action.day,
      };
    case 'SELECT_RESTAURANT':
      return {
        ...state,
        restaurant: action.restaurant,
        id: action.id,
        address: action.address,
      };
    case 'TYPING_TEXT':
      return {
        ...state,
        content: action.text,
      };
    case 'RATE':
      return {
        ...state,
        rating: action.rating,
      };
    case 'SET_SCOPE':
      return {
        ...state,
        scope: action.scope,
      };
    case 'SET_TAGS':
      return {
        ...state,
        tags: [...state.tags, action.tags],
      };
    default:
      return state;
  }
};

export const ReviewWriteContext: React.Context<{}> = createContext({});
export const DispatchReviewWriteContext = createContext(undefined);

function ReviewWriteProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, initState);
  return (
    <DispatchReviewWriteContext.Provider value={dispatch}>
      <ReviewWriteContext.Provider value={state}>{children}</ReviewWriteContext.Provider>
    </DispatchReviewWriteContext.Provider>
  );
}

export const useReviewWrite = () => [useContext(ReviewWriteContext), useContext(DispatchReviewWriteContext)];

type ActionType = {
  type: string;
  year?: number;
  month?: number;
  date?: number;
  day?: string;
  restaurant?: string;
  text?: string;
  rating?: number;
  scope?: 'all' | 'friends';
  id?: number;
  address?: string;
  tags?: [];
};

export default ReviewWriteProvider;
