import { render, screen } from '@testing-library/react';
import ImgUpload from './ImgUpload';

describe('ImgUpload', () => {
  it('사진 입력 폼에는 사진을 업로드해주세요 라는 텍스트가 포함된다.', () => {
    render(<ImgUpload />);
    screen.getByText('사진을 업로드해주세요');
  });

  it('사진 업로드버튼에는 사진 업로드 텍스트가 나타난다.', () => {
    render(<ImgUpload />);
    screen.getByText('사진 업로드');
  });
});
