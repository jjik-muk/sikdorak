import Store from '../Store/Store';
import { Wrap } from './Stores.styled';

function Stores({ stores }: any) {
  const hasStores = stores.length > 0;
  return (
    <Wrap>
      {hasStores &&
        stores.map(({ id, storeName, contactNumber, roadAddressName, reviewCounts, reviewScoreAverage }) => (
          <div key={id}>
            <Store
              id={id}
              storeName={storeName}
              contactNumber={contactNumber}
              roadAddressName={roadAddressName}
              reviewCounts={reviewCounts}
              reviewScoreAverage={reviewScoreAverage}
            />
          </div>
        ))}
    </Wrap>
  );
}

export default Stores;
