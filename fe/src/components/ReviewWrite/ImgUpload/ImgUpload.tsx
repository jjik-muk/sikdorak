import { Button } from '@mui/material';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import useUploadImage from 'hooks/useUploadImage';
import { Dispatch } from 'react';
import { ImgUploadWrapper } from './ImgUpload.styled';

function ImgUpload({ setSelectedImg }: ImgUploadProps) {
  const [, dispatchReviewWrite] = useReviewWrite();
  const { fetchPresignedUrl } = useUploadImage();

  return (
    <ImgUploadWrapper>
      <Button variant="contained" component="label">
        사진 업로드
        <input hidden type="file" id="upload" accept=".png, .jpg, .jpeg" onChange={handleUploadImg} />
      </Button>
    </ImgUploadWrapper>
  );

  async function handleUploadImg(e) {
    const selectedImg = e.target.files[0];
    const extension = selectedImg.type.split('/')[1];
    const allowedExtensions = ['png', 'jpg', 'jpeg'];

    if (!allowedExtensions.includes(extension)) {
      alert('허용된 확장자(png, jpg, jpeg)가 아닙니다.');
      return;
    }

    setSelectedImg(selectedImg);
    const presignedUrl = await fetchPresignedUrl({ extension: 'png' });
    const fileName = presignedUrl.split('/')[4].split('?')[0];
    const imageUrl = `https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/${fileName}`;
    const images = [imageUrl];
    dispatchReviewWrite({ type: 'SET_IMAGES', images });
    dispatchReviewWrite({ type: 'SET_PRESIGNED_URL', presignedUrl });
  }
}

export default ImgUpload;

type ImgUploadProps = {
  setSelectedImg?: Dispatch<any>;
};
