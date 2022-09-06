import Icon from 'components/Common/Icon/Icon';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import useUploadImage from 'hooks/useUploadImage';
import { Dispatch } from 'react';
import { ImgUploadWrapper, ButtonWrapper, Label } from './ImgUpload.styled';

function ImgUpload({ setSelectedImg }: ImgUploadProps) {
  const [, dispatchReviewWrite] = useReviewWrite();
  const { fetchPresignedUrl } = useUploadImage();

  return (
    <ImgUploadWrapper>
      <Icon icon="Photo" fill="black" />
      <div>사진을 업로드해주세요</div>
      <ButtonWrapper id="upload" type="file" accept=".png, .jpg, .jpeg" onChange={handleUploadImg} />
      <Label htmlFor="upload">사진 업로드</Label>
    </ImgUploadWrapper>
  );

  async function handleUploadImg(e) {
    const selectedImg = e.target.files[0];
    const extension = selectedImg.type.split('/')[1];
    const allowedExtensions = ['png', 'jpg', 'jpeg'];

    if (!allowedExtensions.includes(extension)) {
      // TODO: alert 에서 유저 알림 레이아웃으로 변경
      alert('허용된 확장자(png, jpg, jpeg)가 아닙니다.');
      return;
    }

    setSelectedImg(selectedImg);
    const presigendUrl = await fetchPresignedUrl({ extension: 'png' });
    const fileName = presigendUrl.split('/')[4].split('?')[0];
    const imageUrl = `https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/${fileName}`;
    const images = [imageUrl];
    dispatchReviewWrite({ type: 'SET_IMAGES', images });
    dispatchReviewWrite({ type: 'SET_PRESIGNED_URL', presigendUrl });
  }
}

export default ImgUpload;

type ImgUploadProps = {
  setSelectedImg?: Dispatch<any>;
};
