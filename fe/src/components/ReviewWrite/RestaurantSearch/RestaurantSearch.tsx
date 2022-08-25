import { DOMAIN } from 'constants/dummyData';
import Modal from 'common/Modal/Modal';
import { useReviewWrite } from 'context/reviewWriteProvider';
import { useEffect, useState } from 'react';
import { debounce, fetchData } from 'utils/utils';
import { RestaurantSearchWrapper, SearchResult, Wrap } from './RestaurantSearch.styled';

export default function RestaurantSearch() {
  const [reviewWriteState, dispatchReviewWriteState] = useReviewWrite();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchText, setSearchText] = useState('');
  const [searchResult, setSearchResult] = useState([]);

  const { restaurant: selectedRestaurant, address: restaurantAddress } = reviewWriteState;

  useEffect(() => {
    debounce(getSearchResult, 150)();
  }, [searchText]);

  const getSearchResult = async () => {
    if (!searchText.length) {
      setIsModalOpen(false);
      return;
    }
    const { data } = await fetchData(`${DOMAIN}/api/stores?storeName=${searchText}`);
    const searchResultData = data.map(({ id, storeName, baseAddress }) => (
      <SearchResult
        key={id}
        data-storename={storeName}
        data-id={id}
        data-address={baseAddress}
        onClick={clickRestaurant}
      >
        {`${storeName} (${baseAddress})`}
      </SearchResult>
    ));
    setSearchResult(searchResultData);
  };

  return (
    <Wrap>
      <div>
        {selectedRestaurant && !isModalOpen ? (
          <RestaurantSearchWrapper
            value={`${selectedRestaurant} (${restaurantAddress})` || ''}
            onClick={() => setIsModalOpen(true)}
          />
        ) : (
          <RestaurantSearchWrapper value={searchText} onChange={handleSearchRestaurant} />
        )}
        {isModalOpen && <Modal>{searchResult}</Modal>}
      </div>
    </Wrap>
  );

  function handleSearchRestaurant({ target }) {
    const { value } = target;
    setSearchText(value);
    setIsModalOpen(true);
  }

  function clickRestaurant({ target }) {
    const { storename, address, id } = target.dataset;
    setIsModalOpen(false);
    dispatchReviewWriteState({ type: 'SELECT_RESTAURANT', restaurant: storename, address, id });
  }
}
