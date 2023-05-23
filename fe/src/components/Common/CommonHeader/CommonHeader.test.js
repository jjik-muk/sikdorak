import { screen } from '@testing-library/react';
import CommonHeader from './CommonHeader';
import { renderWithProviders } from 'utils/test';
import store from 'store/modules';
import { setMyInfo } from 'store/modules/account';
import { ALT } from 'constants/alt';

describe('Common header', () => {
  it('로고를 표시한다.', () => {
    renderWithProviders(<CommonHeader />);
    expect(screen.getByAltText(ALT.LOGO)).toBeInTheDocument();
  });
  it('홈 아이콘을 표시한다.', () => {
    renderWithProviders(<CommonHeader />);
    expect(screen.getByTestId('Home-icon')).toBeInTheDocument();
  });
  it('지도 아이콘을 표시한다.', () => {
    renderWithProviders(<CommonHeader />);
    expect(screen.getByTestId('Map-icon')).toBeInTheDocument();
  });
  it('리뷰 추가 아이콘을 표시한다.', () => {
    renderWithProviders(<CommonHeader />);
    expect(screen.getByTestId('PostBtn-icon')).toBeInTheDocument();
  });
  it('로그인하지 않은 경우 로그인 버튼을 표시한다.', () => {
    renderWithProviders(<CommonHeader />);
    expect(screen.getByText('로그인')).toBeInTheDocument();
  });
  it('로그인 한 경우 본인의 프로필 사진을 표시한다.', () => {
    store.dispatch(setMyInfo({ id: 1, nickname: '럼카', profileImage: 'Image URL' }));
    renderWithProviders(<CommonHeader />);
    expect(screen.getByAltText('profile picture')).toBeInTheDocument();
  });
});
