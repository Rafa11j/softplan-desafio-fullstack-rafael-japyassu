import React, { useState, useEffect, useCallback } from 'react';
import { Table, Tag, Button, Dropdown, Menu } from 'antd';
import { EyeOutlined, PlusOutlined, TeamOutlined } from '@ant-design/icons';
import { FaEllipsisV } from 'react-icons/fa';
import { ColumnsType } from 'antd/lib/table';
import { useHistory } from 'react-router-dom';

import api from '../../services/api';

import './styles.scss';
import { ProcessState } from '../../enums/enums';
import IProcess from '../../interfaces/process';

const Process: React.FC = () => {
  const history = useHistory();
  const [loading, setLoading] = useState(false);
  const [process, setProcess] = useState<IProcess[]>([]);

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
            onClick={() => navigate(`processos/${data.id}`)}
          >
            Visualizar
          </Menu.Item>
          {(data.state === ProcessState.IN_PROGRESS ||
            data.state === ProcessState.OPEN) && (
            <Menu.Item
              icon={<TeamOutlined />}
              onClick={() => navigate(`processos/${data.id}/designar`)}
            >
              Designar à usuários
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
    },
    {
      title: 'Vara',
      dataIndex: 'stick',
      key: 'stick',
      align: 'center',
      responsive: ['lg'],
    },
    {
      title: 'Tipo de Processo',
      dataIndex: 'processType',
      key: 'processType',
      align: 'center',
      render: (processType: string) => (
        <Tag color="processing">{processType}</Tag>
      ),
      responsive: ['md'],
    },
    {
      title: 'Status',
      dataIndex: 'state',
      key: 'state',
      align: 'center',
      render: (state: string) => (
        <Tag
          color={
            state === ProcessState.OPEN
              ? 'gold'
              : state === ProcessState.IN_PROGRESS
              ? 'cyan'
              : 'success'
          }
        >
          {state === ProcessState.OPEN
            ? 'Aberto'
            : state === ProcessState.IN_PROGRESS
            ? 'Em Andamento'
            : 'Finalizado'}
        </Tag>
      ),
      responsive: ['md'],
    },
    {
      title: 'Controle',
      key: 'control',
      align: 'center',
      render: (text: string, record: IProcess) => (
        <Dropdown
          placement="bottomCenter"
          trigger={['click']}
          overlay={() => menu(record)}
        >
          <Button type="text" shape="circle">
            <FaEllipsisV />
          </Button>
        </Dropdown>
      ),
    },
  ] as ColumnsType<IProcess> | undefined;

  useEffect(() => {
    async function loadProcess() {
      setLoading(true);
      const response = await api.get('/process');
      setTimeout(() => {
        setProcess(response.data.data);
        setLoading(false);
      }, 1500);
    }

    loadProcess();
  }, []);

  return (
    <div className="users-page">
      <div className="page-header">
        <h2>Processos</h2>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => navigate('processos/cadastro')}
        >
          Novo Processo
        </Button>
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

export default Process;
