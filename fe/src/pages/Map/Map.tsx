import { useState } from 'react';

function Map() {
  const [value, setValue] = useState('');
  return <input onChange={handleChange} value={value} />;

  function handleChange(e) {
    setValue(e.target.value);
    console.log(value);
  }
}

export default Map;
