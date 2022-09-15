import Modal from 'components/Common/Modal/Modal';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import useSearchBar from 'hooks/useSearchBar';
import { useEffect, useState } from 'react';
import SearchResult from '../SearchResult/SearchResult';
import { RestaurantSearchInput, Wrap } from './RestaurantSearch.styled';

export default function RestaurantSearch() {
  const [reviewWriteContext] = useReviewWrite();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const headers = { Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API_KEY}` };

  useEffect(() => {
    setInputValue(reviewWriteContext.store.storeName);
  }, []);

  useEffect(() => {
    const hasInputValue = inputValue?.length > 0;
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
        <RestaurantSearchInput
          value={inputValue}
          onChange={handleChangeSearchBar}
          onClick={handleClickSearchBar}
          onFocus={openModal}
          onBlur={closeModal}
        />
        {isModalOpen && (
          <Modal>
            <SearchResult
              searchResults={searchResults}
              inputValue={inputValue}
              setInputValue={setInputValue}
              closeModal={closeModal}
            />
          </Modal>
        )}
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
  function handleChangeSearchBar({ target }) {
    const { value } = target;
    setInputValue(value);

    if (!isModalOpen) {
      openModal();
    }
  }
}
