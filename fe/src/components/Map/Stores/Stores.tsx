import Store from '../Store/Store';
import { Wrap } from './Stores.styled';

function Stores({ stores }: any) {
  const hasStores = stores.length > 0;
  return (
    <Wrap>
      {hasStores &&
        stores.map((store) => (
          <div key={store.id}>
            <Store store={store} />
          </div>
        ))}
    </Wrap>
  );
}

export default Stores;
