import React, { useCallback } from 'react';
import { Layout, Menu, Dropdown, Button } from 'antd';
import { FaHome, FaUsers, FaGavel, FaSignOutAlt } from 'react-icons/fa';
import { Link, useHistory } from 'react-router-dom';
import { useAuth } from '../../hooks/auth';
import { UserTypes } from '../../enums/enums';

import './styles.scss';
import profile from '../../assets/man.png';

const { Header } = Layout;

const AppHeader: React.FC = () => {
  const { user, signOut } = useAuth();
  const history = useHistory();

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

  const renderItemsMenuMobile = useCallback(() => {
    if (user.userType === UserTypes.ADMINISTRATOR) {
      return (
        <Menu.Item>
          <Link to="/usuarios">Usuários</Link>
        </Menu.Item>
      );
    }
    if (user.userType === UserTypes.TRIATOR) {
      return (
        <Menu.Item>
          <Link to="/processos">Processos</Link>
        </Menu.Item>
      );
    }
    return (
      <Menu.Item>
        <Link to="/meus-processos">Meus Processos</Link>
      </Menu.Item>
    );
  }, [user.userType]);

  const menu = (
    <Menu>
      <Menu.Item>
        <Link to="/inicio">Início</Link>
      </Menu.Item>
      {renderItemsMenuMobile()}
      <Menu.Item onClick={signOut}>Sair</Menu.Item>
    </Menu>
  );

  return (
    <Header className="app-header">
      <div className="header-logo" onClick={() => history.push('/inicio')}>
        <h2>ADVSoft</h2>
      </div>
      <div className="header-actions">
        <ul>
          <Link to="/inicio" className="menu-item">
            <FaHome />
            Início
          </Link>
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
      <div className="header-actions-mobile">
        <ul>
          <li className="menu-item-profile">
            <Dropdown
              overlay={menu}
              placement="bottomCenter"
              trigger={['click']}
            >
              <Button shape="round" className="btn-profile-mobile">
                {user.name}
              </Button>
            </Dropdown>
          </li>
        </ul>
      </div>
    </Header>
  );
};

export default AppHeader;
