import { useState, useCallback } from 'react';
import { debounce, fetchData } from 'utils/utils';

const INIT_STATE: any = {};

function useSearchBar() {
  const [inputValue, setInputValue] = useState('');
  const [searchResults, setSearchResults] = useState(INIT_STATE);
  const DELAY_MS = 150;

  const debouncedSearch = useCallback(({ url, headers }: any) => {
    debounce(async function fetchSearchResults() {
      const options = { headers };
      const searchRes = await fetchData(url, options);
      setSearchResults(searchRes);
    }, DELAY_MS)();
  }, []);

  function clearSearchResults() {
    setSearchResults({});
  }

  return { inputValue, setInputValue, searchResults, debouncedSearch, clearSearchResults };
}

export default useSearchBar;
