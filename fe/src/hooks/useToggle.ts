import { useState } from 'react';

function useToggle(initialState) {
  const [state, setState] = useState(initialState);

  function toggleState() {
    setState(!state);
  }

  return [state, toggleState];
}

export default useToggle;
