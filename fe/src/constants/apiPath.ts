export const API_PATH = {
  REVIEW: {
    LIKE: (reviewId: number) => `api/reviews/${reviewId}/like`,
    UNLIKE: (reviewId: number) => `api/reviews/${reviewId}/unlike`,
    DELETE: (reviewId: number) => `api/reviews/${reviewId}`,
  },
  USER: {
    MY_PROFILE: 'api/users/me',
  },
  LOGIN: {
    REISSUE_ACCESS_TOKEN: 'api/oauth/refresh',
  },
};
