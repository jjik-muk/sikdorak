import { DEFAULT_IMG, STORE } from 'constants/dummyData';
import { STORE_DETAIL } from 'styles/size';
import { TEXT } from 'constants/text';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';
import { COLOR } from 'styles/color';

function StorePictures() {
  const { storePictures } = STORE;
  const [firstImg, ...otherImg] = getImagesOfStore({ pictures: storePictures, defaultImg: DEFAULT_IMG });

  return (
    <PictureWrap>
      <div>
        <img src={firstImg} alt={TEXT.ALT.PHOTOGRAPH} width={STORE_DETAIL.IMG.LARGE} height={STORE_DETAIL.IMG.LARGE} />
      </div>
      <OtherPicture>
        {otherImg.map((picture) => (
          <img src={picture} alt={TEXT.ALT.PHOTOGRAPH} width={STORE_DETAIL.IMG.SMALL} height={STORE_DETAIL.IMG.SMALL} />
        ))}
      </OtherPicture>
      <MoreDim />
      <DimText>더 보기</DimText>
    </PictureWrap>
  );

  function getImagesOfStore({ pictures, defaultImg }) {
    const ARR_LEN = 5;
    return Array.from({ length: ARR_LEN }).map((_, i) => pictures[i] || defaultImg);
  }
}

export default StorePictures;

const PictureWrap = styled.div`
  position: relative;
  display: flex;
  width: 800px;
  height: 400px;
  cursor: pointer;
`;

const OtherPicture = styled.div`
  width: 400px;
  height: 400px;
  display: grid;
  grid-template-columns: 1fr 1fr;
`;

export const MoreDim = styled.div`
  position: absolute;
  width: 200px;
  height: 200px;
  background-color: grey;
  opacity: 0.5;
  right: 0;
  bottom: 0;
`;

export const DimText = styled.div`
  position: absolute;
  width: 200px;
  height: 200px;
  ${() => flexLayoutMixin('', 'center', 'center')}
  color: ${COLOR.WHITE};
  font-weight: 500;
  right: 0;
  bottom: 0;
  font-size: 20px;
`;
