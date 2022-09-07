import { DOMAIN } from 'constants/dummyData';
import { useState } from 'react';
import { fetchDataThatNeedToLogin } from 'utils/utils';

function useUploadImage() {
  const [selectedImg, setSelectedImg] = useState(null);

  async function fetchPresignedUrl({ extension }) {
    const res = await fetchDataThatNeedToLogin(`${DOMAIN}/api/images/url`, {
      method: 'PUT',
      bodyData: { extension },
    });
    return res.data.presignedUrl;
  }

  async function uploadImageToS3(presigendUrl, image) {
    const res = await fetch(presigendUrl, {
      method: 'PUT',
      body: image,
      headers: {
        'Content-Type': 'image/png',
      },
    });
    return res;
  }

  return { selectedImg, setSelectedImg, fetchPresignedUrl, uploadImageToS3 };
}

export default useUploadImage;
