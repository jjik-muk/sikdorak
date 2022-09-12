import Store from '../Store/Store';
import { Wrap } from './Stores.styled';

function Stores({ stores }: any) {
  return (
    <Wrap>
      {stores.map(({ id, storeName, contactNumber, roadAddressName }) => (
        <div key={id}>
          <Store id={id} storeName={storeName} contactNumber={contactNumber} roadAddressName={roadAddressName} />
        </div>
      ))}
    </Wrap>
  );
}

export default Stores;
