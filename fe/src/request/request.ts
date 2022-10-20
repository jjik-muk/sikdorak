import { fetchData } from 'utils/fetch';

export const requestReviewModification = async ({ reviewId, body }: { reviewId: number; body: ReviewModificationBodyType }) => {
  const res = await fetchData({ path: `api/reviews/${reviewId}`, method: 'PUT', bodyData: body, withAccessToken: true });
  return res;
};

type ReviewModificationBodyType = {
  reviewContent: string;
  storeId: number;
  reviewScore: number;
  reviewVisibility: string;
  visitedDate: string;
  tags: string[];
  images: string[];
};
