import { Button, TextField } from '@mui/material';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import Feeds from 'components/Common/Feeds/Feeds';
import Modal from 'components/Common/Modal/Modal';
import MapComponent from 'components/Map/MapComponent';
import Stores from 'components/Map/Stores/Stores';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useReviewsBasedLocation } from 'hooks/useReviewsBasedLocation';
import useSearchBar from 'hooks/useSearchBar';
import { useStores, MAP_POS_DEFAULT } from 'hooks/useStores';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Z_INDEX } from 'styles/zIndex';
import { compareTwoObjects, createKey } from 'utils/utils';
import { ContentArea, FeedsArea, MapArea } from './Map.styled';

const TABS = [{ label: '가게 목록' }, { label: '유저 리뷰' }];

function Map() {
  const [activeTabIdx, setActiveTabIdx] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isMovedMap, setIsMovedMap] = useState(false);
  const { stores, mapPos, setMapPos, fetchAndSetStores, isLastPage } = useStores();
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const { dispatchReviews } = useReviews();
  useAuth();

  const { reviews, fetchAndSetReviews, userId, setUserId, isLastPageReview } = useReviewsBasedLocation();

  const isSelectedRestaurantList = activeTabIdx === 0;
  const isSelectedUserReviews = activeTabIdx === 1;

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
    fetchAndSetStores({ saveMethod: 'OVERWRITE' });
  }, []);

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
      <CommonHeader dispatchReviews={dispatchReviews} />
      <ContentArea>
        <Tabs orientation="vertical" variant="scrollable" value={activeTabIdx} sx={{ borderRight: 1, borderColor: 'divider' }}>
          {TABS.map(({ label }, idx) => (
            <div key={createKey(label, idx)}>
              <Tab
                label={label}
                onClick={() => {
                  setActiveTabIdx(idx);
                }}
              />
            </div>
          ))}
        </Tabs>
        <FeedsArea>
          {/* 유저 리뷰 */}
          {isSelectedUserReviews && (
            <>
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
            </>
          )}

          {/* 가게 목록 */}
          {isSelectedRestaurantList && <Stores stores={stores} />}
          {isSelectedRestaurantList && !isLastPage && (
            <Button variant="text" onClick={handleMoreSearch}>
              검색결과 더 보기
            </Button>
          )}
        </FeedsArea>
        <MapArea>
          <MapComponent stores={stores} mapPos={mapPos} setMapPos={setMapPos} />
          {isMovedMap && (
            <Button variant="contained" onClick={handleSearchAgain} sx={{ position: 'absolute', top: '16px', left: '48%', zIndex: Z_INDEX.MAP_SEARCH_BTN }}>
              현 지도에서 재검색
            </Button>
          )}
        </MapArea>
      </ContentArea>
    </>
  );

  function handleMoreSearchReviews() {
    fetchAndSetReviews({ saveMethod: 'ACCUMULATE' });
  }
  function handleMoreSearch() {
    fetchAndSetStores({ saveMethod: 'ACCUMULATE' });
  }
  function handleSearchAgain() {
    fetchAndSetStores({ saveMethod: 'OVERWRITE' });
    setIsMovedMap(false);
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

function UserSearchResultList({ id, nickname, profileImage, setUserId, setInputValue }: any, idx: number) {
  return (
    <Wrap key={createKey(id, idx)} onMouseDown={handleUserId}>
      <Profile src={profileImage} alt="profile" width={30} height={30} />
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

const UserSearchBarWrap = styled.div`
  position: relative;
`;

export default Map;
