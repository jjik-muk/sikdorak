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
import { Buttons, ContentArea, FeedsArea, Input, MapArea } from './Map.styled';

function Map() {
  const [isActiveStore, setIsActiveStore] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { stores, mapPos, setMapPos } = useStores();
  const { inputValue, searchResults, setInputValue, debouncedSearch } = useSearchBar();
  useAuth();
  const { dispatchReviews } = useReviews();

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
        <Buttons>
          <button
            onClick={() => {
              setIsActiveStore(true);
            }}
            type="button"
          >
            가게 목록
          </button>
          <button
            onClick={() => {
              setIsActiveStore(false);
            }}
            type="button"
          >
            유저 리뷰
          </button>
        </Buttons>
        <FeedsArea>
          {!isActiveStore && (
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
          {isActiveStore ? <Stores stores={stores} /> : <Feeds reviews={[]} isUsedMapPage />}
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
