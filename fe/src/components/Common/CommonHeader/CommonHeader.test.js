import { screen } from '@testing-library/react';
import CommonHeader from './CommonHeader';
import { login, renderWithProviders } from 'utils/test';
import { ALT, GET_ALT } from 'constants/alt';

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
    const nickname = '럼카';
    login({ id: 1, nickname });
    renderWithProviders(<CommonHeader />);
    expect(screen.getByAltText(GET_ALT.PROFILE(nickname))).toBeInTheDocument();
  });
});
