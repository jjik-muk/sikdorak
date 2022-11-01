import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
  const navigate = useNavigate();

  useEffect(initMap, []);
  useEffect(handleOverlay, [mapPos, stores]);

  function initMap() {
    const center = new kakao.maps.LatLng(mapPos.lat, mapPos.lng);
    const mapOption = { center, level: 3 };
    const map = new kakao.maps.Map(mapRef.current, mapOption);

    kakao.maps.event.addListener(map, 'dragend', updateCenterPosition);
    setMapState(map);

    function updateCenterPosition() {
      const latlng = map.getCenter();
      const newMapPos = { lat: latlng.getLat(), lng: latlng.getLng() };
      setMapPos(newMapPos);
    }
  }

  function handleOverlay() {
    if (!stores) return;

    const newPositions = stores.map(createPosition);
    const newOverlays = newPositions.map(createCustomOverlay);

    setOverlays(newOverlays);
    overlays?.forEach(removeOverlay);
    newOverlays.forEach(drawOverlay);

    function removeOverlay(overlay) {
      return overlay.setMap(null);
    }
    function createPosition({ id, storeName, x, y, storeId }) {
      return {
        content: createMarkerEl({ text: storeName, id: id || storeId }),
        position: new kakao.maps.LatLng(y + 0.00005, x - 0.00003),
        map: mapState,
      };
    }
    function createMarkerEl({ text, id }) {
      const markerEl = document.createElement('div');
      markerEl.innerText = text;
      markerEl.setAttribute('style', overlayStyle);
      markerEl.setAttribute('class', 'custom-overlay');
      markerEl.addEventListener('click', handleMarkerClick);
      markerEl.addEventListener('mouseover', handleMouseOver);
      markerEl.addEventListener('mouseleave', handleMouseLeave);
      return markerEl;

      function handleMouseOver() {
        this.style.transform = 'scale(1.1)';
      }
      function handleMouseLeave() {
        this.style.transform = 'scale(1)';
      }

      function handleMarkerClick() {
        navigate(`/store/${id}`);
      }
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
