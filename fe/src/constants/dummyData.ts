export const STORE = {
  storeName: '호이 초밥',
  storeRating: 4.5,
  storePictures: [
    'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000370/img/basic/a0000370_main.jpg?20201002142956&q=80&rw=750&rh=536',
    'https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/00/a0000881/img/ko/a0000881_parts_586c7364bbcdc.jpg?20200630185341&q=80&rw=686&rh=490',
    'https://blog.kakaocdn.net/dn/u8bU3/btq9nhUNEgR/kBXjA4SUp2WFK3AiXRzN4k/img.png',
  ],
  reviewCnt: 451,
  address: '인천 계양구 봉오대로 559 1층',
  phoneNumber: '010-1234-5678',
};

export const DEFAULT_IMG = 'http://www.mth.co.kr/wp-content/uploads/2014/12/default-placeholder-300x300.png';

export const REVIEWS = [
  {
    user: {
      userId: 1,
      userNickname: '전용성',
      userProfileImage: 'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg',
    },
    store: {
      storeId: 337,
      storeName: '호이',
      storeAddress: '제주특별자치도 제주시 한경면 저지리 1715-16',
      x: 127.1231,
      y: 34.12312,
    },
    like: {
      count: 1,
      userLikeStatus: true,
    },
    reviewId: 1,
    reviewContent: 'asdfasfd',
    reviewScore: 5,
    reviewVisibility: 'PUBLIC',
    visitedDate: '2022-09-03',
    tags: ['1'],
    images: ['https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/0bc5b0f6-0825-4492-be6c-43844a12c7ee.png'],
    createdAt: '2022-09-12T01:50:06.011286',
    updatedAt: '2022-09-12T01:50:06.011286',
  },
  {
    user: {
      userId: 1,
      userNickname: '전용성',
      userProfileImage: 'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg',
    },
    store: {
      storeId: 337,
      storeName: '호이',
      storeAddress: '제주특별자치도 제주시 한경면 저지리 1715-16',
    },
    like: {
      count: 1,
      userLikeStatus: true,
    },
    reviewId: 1,
    reviewContent: 'asdfasfd',
    reviewScore: 5,
    reviewVisibility: 'PUBLIC',
    visitedDate: '2022-09-03',
    tags: ['1'],
    images: ['https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/0bc5b0f6-0825-4492-be6c-43844a12c7ee.png'],
    createdAt: '2022-09-12T01:50:06.011286',
    updatedAt: '2022-09-12T01:50:06.011286',
  },
];
