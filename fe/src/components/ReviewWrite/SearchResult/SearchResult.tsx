import SearchResultList from '../SearchResultList/SearchResultList';

function SearchResult({ searchResults, inputValue, setInputValue, closeModal }: any) {
  return searchResults.documents?.map(({ id, place_name, address_name, x, y }) => (
    <SearchResultList
      placeId={id}
      storeName={place_name}
      address={address_name}
      x={x}
      y={y}
      inputValue={inputValue}
      setInputValue={setInputValue}
      closeModal={closeModal}
    />
  ));
}

export default SearchResult;
