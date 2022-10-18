export const API_PATH = {
  REVIEW: {
    LIKE: (reviewId: number) => `api/reviews/${reviewId}/like`,
    UNLIKE: (reviewId: number) => `api/reviews/${reviewId}/unlike`,
  },
  USER: {
    MY_PROFILE: 'api/users/me',
  },
};
