const ALT = {
  LOGO: 'sikdorak 글자 로고',
  PHOTOGRAPH: '유저가 업로드 한 음식 또는 가게 사진',
};

const GET_ALT = {
  PROFILE: (nickname: string) => `${nickname}님의 프로필 사진`,
  FOOD: (nickname: string) => `${nickname}님이 업로드 한 음식 사진`,
};

export { ALT, GET_ALT };
