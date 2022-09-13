import { useState } from 'react';
import { debounce, fetchData } from 'utils/utils';

function useSearchBar() {
  const [inputValue, setInputValue] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const DELAY_MS = 150;

  function debouncedSearch({ url, headers }) {
    debounce(async function fetchSearchResults() {
      const options = { headers };
      const searchRes = await fetchData(url, options);
      setSearchResults(searchRes);
      console.log('searched');
    }, DELAY_MS)();
  }

  function clearSearchResults() {
    setSearchResults([]);
  }

  return { inputValue, setInputValue, searchResults, debouncedSearch, clearSearchResults };
}

export default useSearchBar;
