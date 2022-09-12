import { REVIEWS } from 'constants/dummyData';
import Feeds from 'components/Common/Feeds/Feeds';
import CommonHeader from 'components/Common/Header/CommonHeader';
import MapComponent from 'components/Map/MapComponent';
import Stores from 'components/Map/Stores/Stores';
import useStores from 'hooks/useStores';
import { useState } from 'react';
import { Buttons, ContentArea, FeedsArea, MapArea } from './Map.styled';

function Map() {
  const [isActiveStore, setIsActiveStore] = useState(true);
  const { stores, mapPos, setMapPos } = useStores();

  return (
    <>
      <CommonHeader />
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
        <FeedsArea>{isActiveStore ? <Stores stores={stores} /> : <Feeds reviews={REVIEWS} isUsedMapPage />}</FeedsArea>
        <MapArea>
          <MapComponent stores={stores} mapPos={mapPos} setMapPos={setMapPos} />
        </MapArea>
      </ContentArea>
    </>
  );
}

export default Map;
