import { useEffect } from 'react';

export function useOutsideClick(element: React.MutableRefObject<HTMLElement | null>, callback: Function) {
  useEffect(() => {
    const handleMouseDown = ({ target }) => {
      if (!element.current || element.current.contains(target)) {
        return;
      }

      callback();
    };

    document.addEventListener('mousedown', handleMouseDown);

    return () => {
      document.removeEventListener('mousedown', handleMouseDown);
    };
  }, [element, callback]);
}
