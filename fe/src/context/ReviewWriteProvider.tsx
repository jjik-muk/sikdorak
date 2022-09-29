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
  store: {},
};

const reducer = (state, action: ActionType) => {
  const { year, month, date, day, rating, scope, text, images, presignedUrl, store } = action;
  switch (action.type) {
    case 'SET_DATE':
      return {
        ...state,
        year,
        month,
        date,
        day,
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
    case 'REMOVE_TAG':
      return {
        ...state,
        tags: [...state.tags.filter((_, idx) => idx !== action.tagIdx)],
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
    case 'SET_STORE':
      return {
        ...state,
        store,
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
  tag?: string;
  prevTag?: string;
  newTag?: string;
  tagIdx?: string;
  images?: string[];
  presignedUrl: string;
  store: {
    placeId: number;
    storeName: string;
    x: string;
    y: string;
  };
};

export default ReviewWriteProvider;
