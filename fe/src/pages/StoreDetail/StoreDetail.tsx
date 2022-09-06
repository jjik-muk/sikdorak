import { DEFAULT_IMG, STORE } from 'constants/dummyData';
import { ICON, STORE_DETAIL } from 'constants/size';
import TEXT from 'constants/text';
import CommonHeader from 'components/Common/Header/CommonHeader';
import Icon from 'components/Common/Icon/Icon';
import {
  DimText,
  InfoWrap,
  MoreDim,
  OtherPicture,
  PictureWrap,
  Row,
  StoreInfo,
  Text,
  Title,
  Wrap,
} from './StoreDetail.styled';

function StoreDetail() {
  const { storeName, storeRating, storePictures, reviewCnt, address, phoneNumber } = STORE;
  const [firstImg, ...otherImg] = getImagesOfStore({ pictures: storePictures, defaultImg: DEFAULT_IMG });

  return (
    <>
      <CommonHeader />
      <Wrap>
        <PictureWrap>
          <div>
            <img src={firstImg} alt={TEXT.ALT.FOOD} width={STORE_DETAIL.IMG.LARGE} height={STORE_DETAIL.IMG.LARGE} />
          </div>
          <OtherPicture>
            {otherImg.map((picture) => (
              <img src={picture} alt={TEXT.ALT.FOOD} width={STORE_DETAIL.IMG.SMALL} height={STORE_DETAIL.IMG.SMALL} />
            ))}
          </OtherPicture>
          <MoreDim />
          <DimText>더 보기</DimText>
        </PictureWrap>
        <StoreInfo>
          <InfoWrap>
            <Title>{storeName}</Title>
            <Row>
              <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={ICON.SMALL} height={ICON.SMALL} />
              <Text>{storeRating} / 5</Text>
              <Text>리뷰 {reviewCnt}</Text>
            </Row>
            <Row>
              <Icon icon="Location" width={ICON.SMALL} height={ICON.SMALL} fill="#000" />
              <Text>{address}</Text>
            </Row>
            <Row>
              <Icon icon="Phone" width={ICON.SMALL} height={ICON.SMALL} fill="#000" />
              <Text>{phoneNumber}</Text>
            </Row>
          </InfoWrap>
        </StoreInfo>
      </Wrap>
    </>
  );

  function getImagesOfStore({ pictures, defaultImg }) {
    const ARR_LEN = 5;
    return Array.from({ length: ARR_LEN }).map((_, i) => pictures[i] || defaultImg);
  }
}

export default StoreDetail;
