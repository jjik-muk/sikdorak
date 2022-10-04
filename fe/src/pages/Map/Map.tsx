import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import Modal from 'components/Common/Modal/Modal';
import MapComponent from 'components/Map/MapComponent';
import Stores from 'components/Map/Stores/Stores';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import useSearchBar from 'hooks/useSearchBar';
import useStores from 'hooks/useStores';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { ContentArea, FeedsArea, Input, MapArea } from './Map.styled';

function Map() {
  const [activeTabIdx, setActiveTabIdx] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { stores, mapPos, setMapPos } = useStores();
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  const { dispatchReviews } = useReviews();
  useAuth();

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
        <Tabs
          orientation="vertical"
          variant="scrollable"
          value={activeTabIdx}
          sx={{ borderRight: 1, borderColor: 'divider' }}
        >
          <Tab
            label="가게 목록"
            onClick={() => {
              setActiveTabIdx(0);
            }}
          />
          <Tab
            label="유저 리뷰"
            onClick={() => {
              setActiveTabIdx(1);
            }}
          />
        </Tabs>
        <FeedsArea>
          {/* 유저 목록 */}
          {activeTabIdx === 0 && (
            <Input
              value={inputValue}
              onChange={handleChangeSearchBar}
              onClick={handleClickSearchBar}
              onFocus={openModal}
              onBlur={closeModal}
            />
          )}
          {isModalOpen && (
            <Modal>
              {searchResults?.data?.map(({ id, nickname, profileImage }) => (
                <UserSearchResultList id={id} nickname={nickname} profileImage={profileImage} />
              ))}
            </Modal>
          )}
          {/* 가게 목록 */}
          {activeTabIdx === 1 ? <Stores stores={stores} /> : <Feeds reviews={[]} isUsedMapPage />}
        </FeedsArea>
        <MapArea>
          <MapComponent stores={stores} mapPos={mapPos} setMapPos={setMapPos} />
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

export default Map;
