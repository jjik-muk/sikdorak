import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: fit-content;
  margin: 0 auto;
`;

export const PictureWrap = styled.div`
  position: relative;
  display: flex;
  width: 800px;
  height: 400px;
  cursor: pointer;
`;

export const OthersPicture = styled.div`
  width: 400px;
  height: 400px;
  display: grid;
  grid-template-columns: 1fr 1fr;
`;

export const StoreInfo = styled.div`
  width: 800px;
  height: fit-content;
  padding: 10px;
  ${STYLE.BOX_CONTAINER}
  margin: 20px 0 20px 0;
`;

export const InfoWrap = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const Text = styled.div``;

export const Row = styled.div`
  display: flex;
  gap: 10px;
`;

export const Title = styled.h2`
  font-size: 20px;
  font-weight: 700;
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
  color: #fff;
  font-weight: 500;
  right: 0;
  bottom: 0;
`;
