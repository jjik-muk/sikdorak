import { render } from '@testing-library/react';
import Button from './Button';

describe('Button', () => {
  it('사진 업로드 버튼에는 사진 업로드 라는 텍스트가 있다.', () => {
    const { container } = render(<Button />);
    expect(container).toHaveTextContent('사진 업로드');
  });
});
