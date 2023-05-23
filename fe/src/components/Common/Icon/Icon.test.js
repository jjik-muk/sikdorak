import { render } from '@testing-library/react';
import Icon, { iconComponents } from './Icon';
import { MESSAGE } from 'constants/message';

describe('Icon', () => {
  it('iconComponents를 정상적으로 표시한다.', () => {
    const icons = Object.keys(iconComponents);

    icons.forEach((icon) => {
      const { container } = render(<Icon icon={icon} />);
      expect(container.firstChild).toBeInTheDocument();
    });
  });
  it('존재하지 않는 아이콘인 경우 에러를 던진다.', () => {
    const icon = 'InvalidIcon';
    expect(() => render(<Icon icon={icon} />)).toThrowError(`${icon} ${MESSAGE.ERROR.CANT_FIND_ICON}`);
  });
});
