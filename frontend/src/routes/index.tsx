import React from 'react';
import { Switch } from 'react-router-dom';

import Route from './Route';

import SignIn from '../pages/SignIn';
import Home from '../pages/Home';
import User from '../pages/User';
import UserForm from '../pages/User/Form';

const Routes: React.FC = () => {
  return (
    <Switch>
      <Route path="/" exact component={SignIn} />
      <Route path="/inicio" exact component={Home} isPrivate />
      <Route path="/usuarios" exact component={User} isPrivate />
      <Route path="/usuarios/cadastro" exact component={UserForm} isPrivate />
      <Route
        path="/usuarios/cadastro/:id"
        exact
        component={UserForm}
        isPrivate
      />
    </Switch>
  );
};

export default Routes;
