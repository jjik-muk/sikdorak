import { renderWithProviders } from 'utils/test';
import FeedCard from './FeedCard';
import { screen } from '@testing-library/react';
import store from 'store/modules';
import { setMyInfo } from 'store/modules/account';
import { REVIEW } from 'constants/dummyData';

describe('Feed card', () => {
  it('리뷰 작성자의 프로필 사진을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByAltText('Reviewer profile picture')).toBeInTheDocument();
  });
  it('리뷰 작성자의 이름을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByText('럼카')).toBeInTheDocument();
  });
  it('별점을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByTestId('rating')).toBeInTheDocument();
  });
  it('리뷰 내용을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByText('리뷰 내용')).toBeInTheDocument();
  });
  it('식당 정보를 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByTestId('restaurant-profile')).toBeInTheDocument();
  });
  it('좋아요 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByLabelText('like')).toBeInTheDocument();
  });
  it('댓글 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByLabelText('comment')).toBeInTheDocument();
  });
  it('공유하기 버튼을 표시한다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByLabelText('share')).toBeInTheDocument();
  });
  it('내가 작성한 리뷰가 아닌 경우 케밥 메뉴를 표시하지 않는다.', () => {
    renderWithProviders(<FeedCard review={REVIEW} />);
    const kebabMenu = screen.queryByTestId('kebab-menu');
    expect(kebabMenu).toBeNull();
  });
  it('내가 작성한 리뷰인 경우 케밥 메뉴를 표시한다.', () => {
    store.dispatch(setMyInfo({ id: 1, nickname: '럼카', profileImage: 'Image URL' }));
    renderWithProviders(<FeedCard review={REVIEW} />);
    expect(screen.getByTestId('kebab-menu')).toBeInTheDocument();
  });
});
