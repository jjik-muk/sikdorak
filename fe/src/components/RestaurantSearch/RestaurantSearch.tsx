import Modal from 'common/Modal/Modal';
import { useState } from 'react';
import { RestaurantSearchWrapper } from './RestaurantSearch.styled';

export default function RestaurantSearch() {
  const [searchText, setSearchText] = useState('');

  const handleSearch = ({ target }) => {
    const { value } = target;
    setSearchText(value);
  };

  return (
    <div>
      <RestaurantSearchWrapper onChange={handleSearch} />
      {searchText && <Modal>{searchText}</Modal>}
    </div>
  );
}
