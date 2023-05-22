import { renderWithProviders } from 'utils/test';
import FeedCard from './FeedCard';
import { screen } from '@testing-library/react';
import store from 'store/modules';
import { setMyInfo } from 'store/modules/account';

const reviewerName = '럼카';
const reviewContent = '리뷰 내용';
const tag = '태그 1';
const mockReview = {
  createdAt: '2023-05-22T06:20:00.413584',
  images: [],
  like: { count: 0, userLikeStatus: false },
  reviewContent: reviewContent,
  reviewId: 2,
  reviewScore: 5,
  reviewVisibility: 'PUBLIC',
  store: { storeId: 337, storeName: '모구모구', addressName: '경기 김포시 무슨동', roadAddressName: '경기 김포시 무슨로' },
  tags: [tag],
  updatedAt: '2023-05-22T06:20:00.413584',
  user: { userId: 1, userNickname: reviewerName, userProfileImage: 'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg' },
  visitedDate: '2023-05-03',
};

describe('Feed card', () => {
  it('리뷰 작성자의 프로필 사진을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByAltText('Reviewer profile picture')).toBeInTheDocument();
  });
  it('리뷰 작성자의 이름을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByText(reviewerName)).toBeInTheDocument();
  });
  it('별점을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByTestId('rating')).toBeInTheDocument();
  });
  it('리뷰 내용을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByText(reviewContent)).toBeInTheDocument();
  });
  it('식당 정보를 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByTestId('restaurant-profile')).toBeInTheDocument();
  });
  it('좋아요 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByLabelText('like')).toBeInTheDocument();
  });
  it('댓글 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByLabelText('comment')).toBeInTheDocument();
  });
  it('공유하기 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByLabelText('share')).toBeInTheDocument();
  });
  it('내가 작성한 리뷰가 아닌 경우 케밥 메뉴를 표시하지 않는다.', () => {
    renderWithProviders(<FeedCard review={mockReview} />);
    const kebabMenu = screen.queryByTestId('kebab-menu');
    expect(kebabMenu).toBeNull();
  });
  it('내가 작성한 리뷰인 경우 케밥 메뉴를 표시한다.', () => {
    store.dispatch(setMyInfo({ id: 1, nickname: '럼카', profileImage: 'Image URL' }));
    renderWithProviders(<FeedCard review={mockReview} />);
    expect(screen.getByTestId('kebab-menu')).toBeInTheDocument();
  });
});
