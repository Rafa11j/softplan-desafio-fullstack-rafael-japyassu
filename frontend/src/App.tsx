import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { useAuth } from './hooks/auth';
import Routes from './routes';
import AppHeader from './components/AppHeader';

const App: React.FC = () => {
  const { user } = useAuth();

  return (
    <BrowserRouter>
      {user && <AppHeader />}
      <Routes />
    </BrowserRouter>
  );
};

export default App;
