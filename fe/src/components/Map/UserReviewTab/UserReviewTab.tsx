import { Button, TextField } from '@mui/material';
import { FeedsArea, MapArea } from 'pages/Map/Map.styled';
import { useEffect, useState } from 'react';
import { compareTwoObjects, createKey } from 'utils/utils';
import styled from 'styled-components';
import { useReviewsBasedLocation } from 'hooks/useReviewsBasedLocation';
import Modal from 'components/Common/Modal/Modal';
import Feeds from 'components/Common/Feeds/Feeds';
import MapComponent from '../MapComponent';
import useSearchBar from 'hooks/useSearchBar';
import { Z_INDEX } from 'styles/zIndex';
import { MAP_POS_DEFAULT } from 'hooks/useStores';
import { GET_ALT } from 'constants/alt';

function UserReviewTab() {
  const [isMovedMap, setIsMovedMap] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { reviews, mapPos, setMapPos, fetchAndSetReviews, userId, setUserId, isLastPageReview, storesOfReviews } = useReviewsBasedLocation();
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const isSelectedUser = userId !== 0;

  useEffect(
    function handleIsMovedState() {
      if (!compareTwoObjects(mapPos, MAP_POS_DEFAULT)) {
        setIsMovedMap(true);
      }
    },
    [mapPos],
  );
  useEffect(() => {
    fetchAndSetReviews({ saveMethod: 'OVERWRITE' });
  }, [userId]);

  useEffect(() => {
    const hasInputValue = inputValue.length > 0;
    if (!hasInputValue) {
      setIsModalOpen(false);
      return;
    }
    debouncedSearch({
      url: `${process.env.REACT_APP_BE_SERVER_URL}/api/users?nickname=${inputValue}`,
    });
  }, [inputValue]);

  return (
    <>
      <FeedsArea>
        <UserSearchBarWrap>
          <TextField
            fullWidth
            label="유저 검색"
            variant="outlined"
            value={inputValue}
            onChange={handleChangeSearchBar}
            onClick={handleClickSearchBar}
            onFocus={openModal}
            onBlur={closeModal}
          />
          {isModalOpen && (
            <Modal fullWidth>
              {searchResults?.data?.map(({ id, nickname, profileImage }) => (
                <UserSearchResultList id={id} nickname={nickname} profileImage={profileImage} setUserId={setUserId} setInputValue={setInputValue} />
              ))}
            </Modal>
          )}
        </UserSearchBarWrap>
        <Feeds reviews={reviews} />
        {!isLastPageReview && (
          <Button variant="text" onClick={handleMoreSearchReviews}>
            검색결과 더 보기
          </Button>
        )}
      </FeedsArea>
      <MapArea>
        <MapComponent stores={storesOfReviews} mapPos={mapPos} setMapPos={setMapPos} />
        {isMovedMap && isSelectedUser && (
          <Button variant="contained" onClick={handleSearchAgain} sx={{ position: 'absolute', top: '16px', left: '48%', zIndex: Z_INDEX.MAP_SEARCH_BTN }}>
            현 지도에서 재검색
          </Button>
        )}
      </MapArea>
      ;
    </>
  );

  function handleSearchAgain() {
    fetchAndSetReviews({ saveMethod: 'OVERWRITE' });
    setIsMovedMap(false);
  }
  function handleMoreSearchReviews() {
    fetchAndSetReviews({ saveMethod: 'ACCUMULATE' });
  }
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

const UserSearchBarWrap = styled.div`
  position: relative;
  padding-bottom: 20px;
`;

function UserSearchResultList({ id, nickname, profileImage, setUserId, setInputValue }: any, idx: number) {
  return (
    <Wrap key={createKey(id, idx)} onMouseDown={handleUserId}>
      <Profile src={profileImage} alt={GET_ALT.PROFILE(nickname)} width={30} height={30} />
      <span>{nickname}</span>
    </Wrap>
  );

  function handleUserId() {
    setUserId(id);
    setInputValue(nickname);
  }
}

const Wrap = styled.div`
  display: flex;
  flex-direction: row;
  padding: 10px;
  align-items: center;
  gap: 10px;
  border-radius: 10px;
  cursor: pointer;
  :hover {
    background-color: #bdc3c7;
  }
`;

const Profile = styled.img`
  border-radius: 50%;
`;

export default UserReviewTab;
