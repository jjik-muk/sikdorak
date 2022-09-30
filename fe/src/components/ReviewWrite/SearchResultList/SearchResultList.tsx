import { useReviewWrite } from 'context/ReviewWriteProvider';
import styled from 'styled-components';

function SearchResultList({ placeId, storeName, address, x, y, inputValue, setInputValue, closeModal }: any) {
  const [, dispatchReviewWrite] = useReviewWrite();

  return (
    <Wrap key={placeId} onMouseDown={handleClickRestaurant}>
      <StoreName storeName={storeName} inputValue={inputValue} />
      <Address>{address}</Address>
    </Wrap>
  );

  function handleClickRestaurant(e) {
    e.stopPropagation();
    const store = { storeName, placeId, address, x, y };
    setInputValue(storeName);
    dispatchReviewWrite({ type: 'SET_STORE', store });
    closeModal();
  }
}

function StoreName({ storeName, inputValue }: any) {
  const isEqualName = storeName === inputValue;
  if (isEqualName) {
    return <Hightlight>{storeName}</Hightlight>;
  }
  return storeName
    .split(inputValue)
    .map((word) => (word ? <span>{word}</span> : <Hightlight>{inputValue}</Hightlight>));
}

export default SearchResultList;

const Wrap = styled.div`
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;

  :hover {
    background-color: #bdc3c7;
  }
`;

const Address = styled.div`
  font-size: 13px;
  margin-top: 2px;
`;

const Hightlight = styled.span`
  font-weight: bold;
  color: #1565c0;
`;
