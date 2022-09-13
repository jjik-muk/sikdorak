import Modal from 'components/Common/Modal/Modal';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import useSearchBar from 'hooks/useSearchBar';
import { useEffect, useState } from 'react';
import { RestaurantSearchWrapper, SearchResult, Wrap } from './RestaurantSearch.styled';

export default function RestaurantSearch() {
  const [, dispatchReviewWriteState] = useReviewWrite();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const headers = { Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API_KEY}` };

  useEffect(() => {
    const hasInputValue = inputValue.length > 0;
    if (!hasInputValue) {
      setIsModalOpen(false);
      return;
    }

    debouncedSearch({
      url: `https://dapi.kakao.com/v2/local/search/keyword.json?query=${inputValue}&category_group_code=FD6`,
      headers,
    });
  }, [inputValue]);

  return (
    <Wrap>
      <div>
        <RestaurantSearchWrapper
          value={inputValue}
          onChange={handleChangeSearchBar}
          onClick={handleClickSearchBar}
          onFocus={openModal}
          onBlur={closeModal}
        />
        {isModalOpen && <Modal>{createSearchResultComponents(searchResults)}</Modal>}
      </div>
    </Wrap>
  );

  function closeModal() {
    setIsModalOpen(false);
  }
  function openModal() {
    setIsModalOpen(true);
  }
  function handleClickSearchBar() {
    openModal();
  }
  function createSearchResultComponents(searchRes) {
    const { documents } = searchRes;
    return documents?.map(({ id, place_name, address_name, x, y }) => (
      <SearchResult
        key={id}
        data-placeid={id}
        data-storename={place_name}
        data-address={address_name}
        data-x={x}
        data-y={y}
        onClick={handleClickRestaurant}
      >
        {`${place_name} (${address_name})`}
      </SearchResult>
    ));
  }
  function handleChangeSearchBar({ target }) {
    const { value } = target;
    setInputValue(value);
    openModal();
  }
  function handleClickRestaurant({ target }) {
    const { storename, address, placeid, x, y } = target.dataset;
    const store = { storeName: storename, placeId: placeid, address, x, y };
    setInputValue(storename);
    dispatchReviewWriteState({ type: 'SET_STORE', store });
    closeModal();
  }
}
