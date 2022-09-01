import MyUserInfoProvider from 'context/MyUserInfoProvider';
import { StrictMode } from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <StrictMode>
    <MyUserInfoProvider>
      <App />
    </MyUserInfoProvider>
  </StrictMode>,
);
