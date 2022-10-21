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
import useSearchBar from 'hooks/useSearchBar';
import useStores from 'hooks/useStores';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Z_INDEX } from 'styles/zIndex';
import { createKey } from 'utils/utils';
import { ContentArea, FeedsArea, MapArea } from './Map.styled';

const TABS = [{ label: '가게 목록' }, { label: '유저 리뷰' }];

function Map() {
  const [activeTabIdx, setActiveTabIdx] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { stores, mapPos, setMapPos, fetchAndSetStores } = useStores();
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const { dispatchReviews } = useReviews();
  useAuth();
  const isSelectedRestaurantList = activeTabIdx === 0;
  const isSelectedUserReviews = activeTabIdx === 1;

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
          <UserSearchBarWrap>
            {isSelectedUserReviews && (
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
            )}
            {isModalOpen && (
              <Modal fullWidth>
                {searchResults?.data?.map(({ id, nickname, profileImage }) => (
                  <UserSearchResultList id={id} nickname={nickname} profileImage={profileImage} />
                ))}
              </Modal>
            )}
          </UserSearchBarWrap>
          {isSelectedRestaurantList ? <Stores stores={stores} /> : <Feeds reviews={[]} />}
        </FeedsArea>
        <MapArea>
          <MapComponent stores={stores} mapPos={mapPos} setMapPos={setMapPos} />
          <Button variant="contained" onClick={fetchAndSetStores} sx={{ position: 'absolute', top: '80%', left: '50%', zIndex: Z_INDEX.MAP_SEARCH_BTN }}>
            현 지도에서 재검색
          </Button>
        </MapArea>
      </ContentArea>
    </>
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

function UserSearchResultList({ id, nickname, profileImage }: any) {
  return (
    <Wrap key={id}>
      <Profile src={profileImage} alt="profile" width={30} height={30} />
      <Nickname>{nickname}</Nickname>
    </Wrap>
  );
}

const Wrap = styled.li`
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

const Nickname = styled.span``;

const UserSearchBarWrap = styled.div`
  position: relative;
`;

export default Map;
