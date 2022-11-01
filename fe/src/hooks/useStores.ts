import { useState } from 'react';
import { fetchData } from 'utils/fetch';

export const MAP_POS_DEFAULT = { lat: 37.509389, lng: 127.105143 };
const PAGING_SIZE = 6;
const RADIUS_METER = 2000;

export function useStores() {
  const [stores, setStores] = useState([]);
  const [mapPos, setMapPos] = useState(MAP_POS_DEFAULT);
  const [afterParam, setAfterParam] = useState(0);
  const [isLastPage, setIsLastPage] = useState(false);

  async function fetchAndSetStores({ saveMethod }: FetchAndSetStoresParamType) {
    const { lat, lng } = mapPos;
    const after = saveMethod === 'OVERWRITE' ? 0 : afterParam;
    const res = await requestStoresBasedLocation({ lat, lng, afterParam: after, radius: RADIUS_METER, pagingSize: PAGING_SIZE });

    setIsLastPage(res.data.page.last);
    if (!res.data.page.last) {
      setAfterParam(res.data.page.next);
    }

    switch (saveMethod) {
      case 'OVERWRITE':
        setStores(res.data.stores);
        break;
      case 'ACCUMULATE':
        setStores([...stores, ...res.data.stores]);
        break;
      default:
        throw new Error('saveMethod의 값이 잘못되었습니다.');
    }
  }

  async function requestStoresBasedLocation({ lat, lng, afterParam, radius, pagingSize }: RequestStoresBasedLocationParamType) {
    const res = await fetchData({ path: `api/stores?type=maps&x=${lng}&y=${lat}&radius=${radius}&after=${afterParam}&size=${pagingSize}` });
    return res;
  }

  return { stores, setStores, mapPos, setMapPos, fetchAndSetStores, setAfterParam, isLastPage };
}

type FetchAndSetStoresParamType = {
  saveMethod: 'OVERWRITE' | 'ACCUMULATE';
};

type RequestStoresBasedLocationParamType = {
  lat: number;
  lng: number;
  afterParam: number;
  radius: number;
  pagingSize: number;
};
