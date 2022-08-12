import Icon from 'common/Icon';
import { ImgUploadWrapper, ButtonWrapper } from './ImgUpload.styled';

function ImgUpload() {
  return (
    <ImgUploadWrapper>
      <Icon icon="Photo" fill="black" />
      <div>사진을 업로드해주세요</div>
      <ButtonWrapper>사진 업로드</ButtonWrapper>;
    </ImgUploadWrapper>
  );
}
export default ImgUpload;
