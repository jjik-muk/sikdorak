import { createContext, useContext, useReducer } from 'react';

const INIT_STATE = {
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
  images: [],
};

const reducer = (state, action: ActionType) => {
  const { year, month, date, day, restaurant, id, address, rating, scope, text, images, presignedUrl } = action;
  switch (action.type) {
    case 'SET_DATE':
      return {
        ...state,
        year,
        month,
        date,
        day,
      };
    case 'SELECT_RESTAURANT':
      return {
        ...state,
        restaurant,
        id,
        address,
      };
    case 'TYPING_TEXT':
      return {
        ...state,
        content: text,
      };
    case 'RATE':
      return {
        ...state,
        rating,
      };
    case 'SET_SCOPE':
      return {
        ...state,
        scope,
      };
    case 'SET_TAGS':
      return {
        ...state,
        tags: [...state.tags, action.tags],
      };
    case 'SET_IMAGES':
      return {
        ...state,
        images,
      };
    case 'SET_PRESIGNED_URL':
      return {
        ...state,
        presignedUrl,
      };
    default:
      return state;
  }
};

export const ReviewWriteContext: React.Context<{}> = createContext({});
export const DispatchReviewWriteContext = createContext(undefined);

function ReviewWriteProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(reducer, INIT_STATE);
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
  scope?: 'public' | 'private';
  id?: number;
  address?: string;
  tags?: [];
  images?: string[];
  presignedUrl: string;
};

export default ReviewWriteProvider;
