import React, { useState, useEffect, useCallback } from 'react';
import { Table, Tag, Button, Dropdown, Menu } from 'antd';
import { EyeOutlined, EditOutlined } from '@ant-design/icons';
import { FaEllipsisV } from 'react-icons/fa';
import { ColumnsType } from 'antd/lib/table';
import { useHistory } from 'react-router-dom';

import api from '../../services/api';

import './styles.scss';
import { ProcessState } from '../../enums/enums';
import { useAuth } from '../../hooks/auth';
import IMyProcess from '../../interfaces/myProcess';
import IProcess from '../../interfaces/process';

const MyProcess: React.FC = () => {
  const history = useHistory();
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [process, setProcess] = useState<IMyProcess[]>([]);

  const navigate = useCallback(
    (path: string) => {
      history.push(`/${path}`);
    },
    [history],
  );

  const menu = useCallback(
    (data: IProcess) => {
      return (
        <Menu>
          <Menu.Item
            icon={<EyeOutlined />}
            onClick={() => navigate(`meus-processos/${data.id}`)}
          >
            Visualizar
          </Menu.Item>
          {(data.state === ProcessState.IN_PROGRESS ||
            data.state === ProcessState.OPEN) && (
            <Menu.Item
              icon={<EditOutlined />}
              onClick={() => navigate(`meus-processos/${data.id}/parecer`)}
            >
              Adicionar Parecer
            </Menu.Item>
          )}
        </Menu>
      );
    },
    [navigate],
  );

  const columns = [
    {
      title: 'Processo',
      dataIndex: 'process',
      key: 'process',
      render: (myProcess: IProcess) => {
        return myProcess.process;
      },
    },
    {
      title: 'Vara',
      dataIndex: 'process',
      key: 'process',
      align: 'center',
      responsive: ['lg'],
      render: (myProcess: IProcess) => {
        return myProcess.stick;
      },
    },
    {
      title: 'Tipo de Processo',
      dataIndex: 'process',
      key: 'process',
      align: 'center',
      render: (myProcess: IProcess) => (
        <Tag color="processing">{myProcess.processType}</Tag>
      ),
      responsive: ['md'],
    },
    {
      title: 'Status',
      dataIndex: 'process',
      key: 'process',
      align: 'center',
      render: (state: IProcess) => (
        <Tag
          color={
            state.state === ProcessState.OPEN
              ? 'gold'
              : state.state === ProcessState.IN_PROGRESS
              ? 'cyan'
              : 'success'
          }
        >
          {state.state === ProcessState.OPEN
            ? 'Aberto'
            : state.state === ProcessState.IN_PROGRESS
            ? 'Em Andamento'
            : 'Finalizado'}
        </Tag>
      ),
      responsive: ['md'],
    },
    {
      title: 'Controle',
      dataIndex: 'process',
      key: 'process',
      align: 'center',
      render: (text: string, record: IMyProcess) => (
        <Dropdown
          placement="bottomCenter"
          trigger={['click']}
          overlay={() => menu(record.process)}
        >
          <Button type="text" shape="circle">
            <FaEllipsisV />
          </Button>
        </Dropdown>
      ),
    },
  ] as ColumnsType<IMyProcess> | undefined;

  useEffect(() => {
    async function loadProcess() {
      setLoading(true);
      const response = await api.get(`/users/${user.id}/process`);
      setTimeout(() => {
        setProcess(response.data.data);
        setLoading(false);
      }, 1500);
    }

    loadProcess();
  }, [user.id]);

  return (
    <div className="users-page">
      <div className="page-header">
        <h2>Meus Processos</h2>
      </div>
      <Table
        columns={columns}
        dataSource={process}
        loading={loading}
        rowKey={record => record.id}
      />
    </div>
  );
};

export default MyProcess;
