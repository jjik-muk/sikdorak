import { FeedsArea, MapArea } from 'pages/Map/Map.styled';
import Stores from '../Stores/Stores';
import { Button } from '@mui/material';
import MapComponent from '../MapComponent';
import { MAP_POS_DEFAULT, useStores } from 'hooks/useStores';
import { compareTwoObjects } from 'utils/utils';
import { useEffect, useState } from 'react';
import { Z_INDEX } from 'styles/zIndex';
import GuideText from 'components/Common/GuideText/GuideText';
import { TEXT } from 'constants/text';

function RestaurantListTab({ activeTabIdx }: { activeTabIdx: number }) {
  const [isMovedMap, setIsMovedMap] = useState(false);
  const { stores, mapPos, setMapPos, fetchAndSetStores, isLastPage } = useStores();
  const hasStores = stores.length > 0;

  useEffect(
    function handleIsMovedState() {
      if (!compareTwoObjects(mapPos, MAP_POS_DEFAULT)) {
        setIsMovedMap(true);
      }
    },
    [mapPos],
  );
  useEffect(function initStores() {
    fetchAndSetStores({ saveMethod: 'OVERWRITE' });
  }, []);
  useEffect(() => {
    fetchAndSetStores({ saveMethod: 'OVERWRITE' });
  }, [activeTabIdx]);

  return (
    <>
      <FeedsArea>
        {hasStores ? <Stores stores={stores} /> : <GuideText text={TEXT.NO_STORES} />}
        {!isLastPage && (
          <Button variant="text" onClick={handleMoreSearch}>
            검색결과 더 보기
          </Button>
        )}
      </FeedsArea>
      <MapArea>
        <MapComponent stores={stores} mapPos={mapPos} setMapPos={setMapPos} />
        {isMovedMap && (
          <Button variant="contained" onClick={handleSearchAgain} sx={{ position: 'absolute', top: '16px', left: '48%', zIndex: Z_INDEX.MAP_SEARCH_BTN }}>
            현 지도에서 재검색
          </Button>
        )}
      </MapArea>
      ;
    </>
  );

  function handleMoreSearch() {
    fetchAndSetStores({ saveMethod: 'ACCUMULATE' });
  }
  function handleSearchAgain() {
    fetchAndSetStores({ saveMethod: 'OVERWRITE' });
    setIsMovedMap(false);
  }
}

export default RestaurantListTab;
