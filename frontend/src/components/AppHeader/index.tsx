import React, { useCallback } from 'react';
import { Layout } from 'antd';
import { FaHome, FaUsers, FaGavel, FaSignOutAlt } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { useAuth } from '../../hooks/auth';
import { UserTypes } from '../../enums/enums';

import './styles.scss';
import profile from '../../assets/man.png';

const { Header } = Layout;

const AppHeader: React.FC = () => {
  const { user, signOut } = useAuth();

  const renderItemsMenu = useCallback(() => {
    if (user.userType === UserTypes.ADMINISTRATOR) {
      return (
        <>
          <Link to="/usuarios" className="menu-item">
            <FaUsers />
            Usuários
          </Link>
        </>
      );
    }
    if (user.userType === UserTypes.TRIATOR) {
      return (
        <>
          <Link to="/processos" className="menu-item">
            <FaGavel />
            Processos
          </Link>
        </>
      );
    }
    return (
      <>
        <Link to="/meus-processos" className="menu-item">
          <FaGavel />
          Meus Processos
        </Link>
      </>
    );
  }, [user.userType]);

  return (
    <Header className="app-header">
      <div className="header-logo">
        <h2>ADVSoft</h2>
      </div>
      <div className="header-actions">
        <ul>
          <li className="menu-item">
            <FaHome />
            Início
          </li>
          {renderItemsMenu()}
          <li className="menu-item" onClick={signOut}>
            <FaSignOutAlt />
            Sair
          </li>
          <li className="menu-item-profile">
            <img src={profile} alt="ProfileImage" />
            {user.name}
          </li>
        </ul>
      </div>
    </Header>
  );
};

export default AppHeader;
