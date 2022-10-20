import { useState } from 'react';
import { fetchData } from 'utils/fetch';

function useUploadImage() {
  const [selectedImg, setSelectedImg] = useState(null);

  async function fetchPresignedUrl({ extension }) {
    const res = await fetchData({ path: `api/images/url`, method: 'PUT', bodyData: { extension }, withAccessToken: true });
    return res.data.preSignedUrl;
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
