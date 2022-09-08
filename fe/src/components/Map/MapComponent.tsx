import { useEffect, useRef, useState } from 'react';
import { createKey } from 'utils/utils';
import { KakaoMap, overlayStyle } from './MapComponent.styled';

declare global {
  interface Window {
    kakao: any;
  }
}

const getRandomNumber = (min: number, max: number) => Math.random() * (max - min) + min;

const { kakao } = window;

const positions = new Array(10).fill(1).map((_, idx) => {
  const lat = getRandomNumber(37.48, 37.495);
  const lng = getRandomNumber(127.03, 127.04);
  const key = createKey(String(lat * lng), idx);
  const template = `럼카의 ${idx + 1}번째 리뷰 식당`;
  return {
    content: `<div key=${key} style="${overlayStyle}">${template}</div>`,
    position: new kakao.maps.LatLng(lat, lng),
  };
});

export default function MapComponent() {
  const [mapState, setMapState] = useState(null);
  const mapRef = useRef(null);

  const initMap = () => {
    const center = new kakao.maps.LatLng(37.490821, 127.033417);
    const options = {
      center,
      level: 3,
    };
    const map = new kakao.maps.Map(mapRef.current, options);
    setMapState(map);
  };

  // const position = new kakao.maps.LatLng(37.490821, 127.033417);
  // const content = `<div style="${overlayStyle}">코드스쿼드</div>`;

  const drawOverlay = () => {
    positions.forEach((pos) => {
      const overlay = new kakao.maps.CustomOverlay(pos);
      overlay.setMap(mapState);
    });
  };

  // const startOverlay = new kakao.maps.CustomOverlay({
  //   position,
  //   content,
  // });

  // startOverlay.setMap(mapState);

  useEffect(initMap, []);
  useEffect(drawOverlay, [mapState]);

  return <KakaoMap ref={mapRef} />;
}
