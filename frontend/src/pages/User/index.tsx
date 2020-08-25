import React, { useState, useEffect, useCallback } from 'react';
import { Table, Tag, Button, Dropdown, Menu, Modal, notification } from 'antd';
import {
  EyeOutlined,
  EditOutlined,
  DeleteOutlined,
  PlusOutlined,
  ExclamationCircleOutlined,
} from '@ant-design/icons';
import { FaEllipsisV } from 'react-icons/fa';
import { ColumnsType } from 'antd/lib/table';
import { useHistory } from 'react-router-dom';

import IUser from '../../interfaces/user';
import api from '../../services/api';

import './styles.scss';
import { UserTypes } from '../../enums/enums';

const { confirm } = Modal;

const User: React.FC = () => {
  const history = useHistory();
  const [loading, setLoading] = useState(false);
  const [users, setUser] = useState<IUser[]>([]);

  const navigate = useCallback(
    (path: string) => {
      history.push(`/${path}`);
    },
    [history],
  );

  const loadUsers = useCallback(async () => {
    setLoading(true);
    const response = await api.get('/users');
    setTimeout(() => {
      setUser(response.data.data);
      setLoading(false);
    }, 1500);
  }, []);

  const deleteUser = useCallback(
    async (id: string) => {
      try {
        await api.delete(`/users/${id}`);
        loadUsers();
      } catch (err) {
        if (err.response !== undefined) {
          if (err.response.data.status === 500) {
            notification.info({
              message: 'Aviso',
              description: 'O usuário está designado para um processo!',
            });
          } else {
            err.response.data.errors.map((erro: string) => {
              notification.warning({
                message: 'Alerta',
                description: erro,
              });
            });
          }
        } else {
          notification.error({
            message: 'Erro',
            description: 'Falha no servidor!',
          });
        }
      }
    },
    [loadUsers],
  );

  const showDeleteConfirm = useCallback(
    (id: string) => {
      confirm({
        title: 'Deseja deletar este usuário?',
        icon: <ExclamationCircleOutlined />,
        okText: 'Sim',
        okType: 'danger',
        cancelText: 'Não',
        onOk() {
          deleteUser(id);
        },
        onCancel() {
          console.log('Cancel');
        },
      });
    },
    [deleteUser],
  );

  const menu = useCallback(
    (id: string) => {
      return (
        <Menu>
          <Menu.Item
            icon={<EyeOutlined />}
            onClick={() => navigate(`usuarios/${id}`)}
          >
            Visualizar
          </Menu.Item>
          <Menu.Item
            icon={<EditOutlined />}
            onClick={() => navigate(`usuarios/cadastro/${id}`)}
          >
            Editar
          </Menu.Item>
          <Menu.Item
            icon={<DeleteOutlined />}
            onClick={() => showDeleteConfirm(id)}
          >
            Deletar
          </Menu.Item>
        </Menu>
      );
    },
    [navigate, showDeleteConfirm],
  );

  const columns = [
    {
      title: 'Nome',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'E-mail',
      dataIndex: 'email',
      key: 'email',
      align: 'center',
      responsive: ['lg'],
    },
    {
      title: 'Tipo de Usuário',
      dataIndex: 'userType',
      key: 'userType',
      align: 'center',
      render: (userType: string) => (
        <Tag
          color={
            userType === UserTypes.ADMINISTRATOR
              ? 'blue'
              : userType === UserTypes.TRIATOR
              ? 'purple'
              : 'gold'
          }
        >
          {userType}
        </Tag>
      ),
      responsive: ['md'],
    },
    {
      title: 'Controle',
      key: 'control',
      align: 'center',
      render: (text: any, record: IUser) => (
        <Dropdown
          placement="bottomCenter"
          trigger={['click']}
          overlay={() => menu(record.id)}
        >
          <Button type="text" shape="circle">
            <FaEllipsisV />
          </Button>
        </Dropdown>
      ),
    },
  ] as ColumnsType<IUser> | undefined;

  useEffect(() => {
    loadUsers();
  }, []);

  return (
    <div className="users-page">
      <div className="page-header">
        <h2>Usuários</h2>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => navigate('usuarios/cadastro')}
        >
          Novo Usuário
        </Button>
      </div>
      <Table
        columns={columns}
        dataSource={users}
        loading={loading}
        rowKey={record => record.id}
      />
    </div>
  );
};

export default User;
