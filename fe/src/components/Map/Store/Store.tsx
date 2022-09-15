import { useNavigate } from 'react-router-dom';
import { Wrap } from './Store.styled';

function Store({ id, storeName, contactNumber, roadAddressName }: any) {
  const navigate = useNavigate();
  return (
    <Wrap
      onClick={() => {
        navigate(`/store/${id}`);
      }}
    >
      <div>{storeName}</div>
      <div>{contactNumber}</div>
      <div>{roadAddressName}</div>
    </Wrap>
  );
}

export default Store;
