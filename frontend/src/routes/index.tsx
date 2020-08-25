import React from 'react';
import { Switch } from 'react-router-dom';

import Route from './Route';

import SignIn from '../pages/SignIn';
import Home from '../pages/Home';
import User from '../pages/User';
import UserForm from '../pages/User/Form';
import Process from '../pages/Process';
import ProcessForm from '../pages/Process/Form';
import ProcessDesignate from '../pages/Process/Designate';
import ProcessDetail from '../pages/Process/Detail';

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
      <Route path="/processos" exact component={Process} isPrivate />
      <Route path="/processos/:id" exact component={ProcessDetail} isPrivate />
      <Route
        path="/processos/:id/designar"
        exact
        component={ProcessDesignate}
        isPrivate
      />
      <Route
        path="/processos/cadastro"
        exact
        component={ProcessForm}
        isPrivate
      />
    </Switch>
  );
};

export default Routes;
