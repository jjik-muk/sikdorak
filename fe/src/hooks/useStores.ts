import { useEffect, useState } from 'react';
import { fetchData } from 'utils/utils';

const MAP_POS_DEFAULT = { x: 37.509389, y: 127.105143 };

function useStores() {
  const [stores, setStores] = useState([]);
  const [mapPos, setMapPos] = useState(MAP_POS_DEFAULT);

  useEffect(() => {
    fetchAndSetStores();

    async function fetchAndSetStores() {
      const res = await fetchData(
        `${process.env.REACT_APP_BE_SERVER_URL}/api/stores?type=maps&x=${mapPos.x}&y=${mapPos.y}&radius=100`,
      );
      setStores(res.data);
    }
  }, [mapPos]);

  return { stores, setStores, mapPos, setMapPos };
}

export default useStores;
