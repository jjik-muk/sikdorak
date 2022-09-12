import Modal from 'components/Common/Modal/Modal';
import { useReviewWrite } from 'context/ReviewWriteProvider';
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
    const { documents } = await fetchData(
      `https://dapi.kakao.com/v2/local/search/keyword.json?query=${searchText}&category_group_code=FD6`,
      {
        headers: { Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API_KEY}` },
      },
    );

    const searchResultData = documents.map(({ id, place_name, address_name, x, y }) => (
      <SearchResult
        key={id}
        data-placeid={id}
        data-storename={place_name}
        data-address={address_name}
        data-x={x}
        data-y={y}
        onClick={clickRestaurant}
      >
        {`${place_name} (${address_name})`}
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
    const { storename, address, placeid, x, y } = target.dataset;
    const store = { storeName: storename, placeId: placeid, address, x, y };
    setIsModalOpen(false);
    dispatchReviewWriteState({ type: 'SET_STORE', store });
  }
}
