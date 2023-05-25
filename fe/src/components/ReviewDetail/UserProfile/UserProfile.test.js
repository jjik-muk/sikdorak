import { renderWithProviders } from 'utils/test';
import UserProfile from './UserProfile';
import { screen } from '@testing-library/react';
import { GET_ALT } from 'constants/alt';
import { DEFAULT_USER_IMG } from 'constants/dummyData';

describe('UserProfile', () => {
  it('유저 프로필 사진을 표시한다.', () => {
    const nickname = '럼카';
    renderWithProviders(<UserProfile imgUrl="imageURL" nickname={nickname} userId={1} />);
    expect(screen.getByAltText(GET_ALT.PROFILE(nickname)).getAttribute('src')).toBe('imageURL');
  });
  it('유저 프로필 사진이 없으면 디폴트 사진을 표시한다.', () => {
    const nickname = '럼카';
    renderWithProviders(<UserProfile nickname={nickname} userId={1} />);
    expect(screen.getByAltText(GET_ALT.PROFILE(nickname)).getAttribute('src')).toBe(DEFAULT_USER_IMG);
  });
  it('유저 이름을 표시한다.', () => {
    const nickname = '럼카';
    renderWithProviders(<UserProfile nickname={nickname} userId={1} />);
    expect(screen.getByText(nickname)).toBeInTheDocument();
  });
});
