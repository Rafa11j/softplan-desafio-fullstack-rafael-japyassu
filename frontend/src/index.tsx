import React from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.less';
import './styles/index.scss';
import App from './App';
import { AuthProvider } from './hooks/auth';

ReactDOM.render(
  <React.StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </React.StrictMode>,
  document.getElementById('root'),
);
