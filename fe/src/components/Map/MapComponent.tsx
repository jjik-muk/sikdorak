import { useEffect, useRef, useState } from 'react';
import { KakaoMap, overlayStyle } from './MapComponent.styled';

declare global {
  interface Window {
    kakao: any;
  }
}

const { kakao } = window;

export default function MapComponent({ stores, mapPos, setMapPos }: any) {
  const [mapState, setMapState] = useState(null);
  const [overlays, setOverlays] = useState([]);
  const mapRef = useRef(null);

  useEffect(initMap, []);
  useEffect(handleOverlay, [mapPos]);

  function initMap() {
    const center = new kakao.maps.LatLng(mapPos.x, mapPos.y);
    const mapOption = { center, level: 3 };
    const map = new kakao.maps.Map(mapRef.current, mapOption);

    kakao.maps.event.addListener(map, 'dragend', updateCenterPosition);
    setMapState(map);

    function updateCenterPosition() {
      const latlng = map.getCenter();
      const newMapPos = { x: latlng.getLat(), y: latlng.getLng() };
      setMapPos(newMapPos);
    }
  }

  function handleOverlay() {
    const newPositions = stores.map(createPosition);
    const newOverlays = newPositions.map(createCustomOverlay);

    setOverlays(newOverlays);
    overlays?.forEach(removeOverlay);
    newOverlays.forEach(drawOverlay);

    function removeOverlay(overlay) {
      return overlay.setMap(null);
    }
    function createPosition({ storeName, x, y }, idx) {
      return {
        content: `<div key=${idx} style="${overlayStyle}">${storeName}</div>`,
        position: new kakao.maps.LatLng(y, x),
        map: mapState,
      };
    }
    function createCustomOverlay(position) {
      return new kakao.maps.CustomOverlay(position);
    }
    function drawOverlay(overlay) {
      return overlay.setMap(mapState);
    }
  }

  return <KakaoMap ref={mapRef} />;
}
