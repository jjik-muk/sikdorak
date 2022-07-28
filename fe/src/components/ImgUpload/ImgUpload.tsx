import Icon from 'common/Icon';
import Button from './Button/Button';
import { ImgUploadWrapper } from './ImgUpload.styled';

function ImgUpload() {
  return (
    <ImgUploadWrapper>
      <Icon icon="Photo" />
      <div>사진을 업로드해주세요</div>
      <Button />
    </ImgUploadWrapper>
  );
}
export default ImgUpload;
