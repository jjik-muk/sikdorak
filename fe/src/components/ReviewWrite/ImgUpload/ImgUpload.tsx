import Icon from 'components/Common/Icon/Icon';
import { Dispatch } from 'react';
import { ImgUploadWrapper, ButtonWrapper, Label } from './ImgUpload.styled';

function ImgUpload({ setSelectedImg }: ImgUploadProps) {
  return (
    <ImgUploadWrapper>
      <Icon icon="Photo" fill="black" />
      <div>사진을 업로드해주세요</div>
      <ButtonWrapper id="upload" type="file" onChange={handleUploadImg} />
      <Label htmlFor="upload">사진 업로드</Label>
    </ImgUploadWrapper>
  );

  function handleUploadImg(e) {
    const selectedImg = e.target.files[0];
    setSelectedImg(selectedImg);
  }
}

export default ImgUpload;

type ImgUploadProps = {
  setSelectedImg?: Dispatch<any>;
};
