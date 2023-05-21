import { useState, useCallback } from 'react';
import { debounce } from 'utils/utils';

function useSearchBar() {
  const [inputValue, setInputValue] = useState('');
  const [searchResults, setSearchResults] = useState<any>({});
  const DELAY_MS = 250;
  const debouncedSearch = useCallback(debounce(fetchSearchResults, DELAY_MS), []);

  async function fetchSearchResults({ url, headers }) {
    const res = await fetch(url, { headers });
    const resJson = await res.json();
    setSearchResults(resJson);
  }

  return { inputValue, setInputValue, searchResults, debouncedSearch };
}

export default useSearchBar;
