import { Wrap } from './Store.styled';

function Store({ storeName, contactNumber, roadAddressName }: any) {
  return (
    <Wrap>
      <div>{storeName}</div>
      <div>{contactNumber}</div>
      <div>{roadAddressName}</div>
    </Wrap>
  );
}

export default Store;
