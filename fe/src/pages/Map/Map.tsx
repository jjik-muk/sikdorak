import CommonHeader from 'components/Common/Header/CommonHeader';
import MapComponent from 'components/Map/MapComponent';
import { ContentArea, FeedsArea, MapArea } from './Map.styled';

function Map() {
  return (
    <>
      <CommonHeader />
      <ContentArea>
        <FeedsArea>야호</FeedsArea>
        <MapArea>
          <MapComponent />
        </MapArea>
      </ContentArea>
    </>
  );
}

export default Map;
