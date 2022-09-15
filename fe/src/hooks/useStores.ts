import { useEffect, useState } from 'react';
import { fetchData } from 'utils/utils';

const MAP_POS_DEFAULT = { x: 37.509389, y: 127.105143 };
const PAGING_SIZE = 5;
const RADIUS = 2000;

function useStores() {
  const [stores, setStores] = useState([]);
  const [mapPos, setMapPos] = useState(MAP_POS_DEFAULT);
  const [afterParam, setAfterParam] = useState(0);

  useEffect(() => {
    fetchAndSetStores();

    async function fetchAndSetStores() {
      const res = await fetchData(
        `${process.env.REACT_APP_BE_SERVER_URL}/api/stores?type=maps&x=${mapPos.y}&y=${mapPos.x}&radius=${RADIUS}&after=${afterParam}&size=${PAGING_SIZE}`,
      );
      setStores(res.data.stores);
      const nextAfterParam = res.data.page.next;
      if (!res.data.page.last) {
        setAfterParam(nextAfterParam);
      }
    }
  }, [mapPos]);

  return { stores, setStores, mapPos, setMapPos };
}

export default useStores;
