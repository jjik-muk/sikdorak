import { useEffect, useReducer } from 'react';

const INIT_STATE = { data: null, error: null };

function useFetch({ url, options }: { url: any; options?: any }) {
  const [state, dispatch] = useReducer(fetchReducer, INIT_STATE);

  useEffect(() => {
    if (!url) {
      throw new Error('useFetch의 매개변수인 url이 없습니다.');
    }
    fetchData();

    async function fetchData() {
      try {
        const res = await fetch(url, options);
        if (!res.ok) {
          throw new Error(res.statusText);
        }
        const resJSON = await res.json();
        dispatch({ type: 'fetch', data: resJSON });
      } catch (error) {
        dispatch({ type: 'error', error });
      }
    }
  }, [url, options]);

  return state;
}

function fetchReducer(state: object, action: any) {
  const { type, data, error, status } = action;

  switch (type) {
    case 'fetch':
      return { ...state, data };
    case 'error':
      return { ...state, error };
    case 'status':
      return { ...state, status };
    default:
      return state;
  }
}

export default useFetch;
