import React, { useState, useEffect, useCallback } from 'react';
import { Button, Card, Row, Col, Divider, Table } from 'antd';
import { useParams, useHistory } from 'react-router-dom';
import { DoubleLeftOutlined } from '@ant-design/icons';
import { ColumnsType } from 'antd/lib/table';
import IProcess from '../../../interfaces/process';
import api from '../../../services/api';

import IUser from '../../../interfaces/user';
import ProcessInfo from '../../../components/ProcessInfo';

interface IUserProcess {
  user: string;
  process: string;
}

const ProcessDetail: React.FC = () => {
  const [process, setProcess] = useState<IProcess>();
  const [usersProcess, setUsersProcess] = useState<IUser[]>();
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const history = useHistory();

  const back = useCallback(() => {
    history.goBack();
  }, [history]);

  const loadProcess = useCallback(async () => {
    setLoading(true);
    const response = await api.get(`/process/${id}/users`);
    setProcess(response.data.data.process);
    setUsersProcess(response.data.data.users);
    setLoading(false);
  }, [id]);

  useEffect(() => {
    loadProcess();
  }, [id, loadProcess]);

  const columns = [
    {
      title: 'Nome',
      dataIndex: 'name',
      key: 'name',
      align: 'center',
    },
    {
      title: 'E-mail',
      dataIndex: 'email',
      key: 'email',
      align: 'center',
      responsive: ['lg'],
    },
  ] as ColumnsType<IUser> | undefined;

  return (
    <div className="process-page">
      <div className="page-header">
        <h2>Dados do Processo</h2>
        <Button type="primary" icon={<DoubleLeftOutlined />} onClick={back}>
          Voltar
        </Button>
      </div>
      <Row>
        <Col span={24}>
          <Card>
            <ProcessInfo process={process} />
          </Card>
        </Col>
      </Row>
      <Row>
        <Col span={24}>
          <Card>
            <Divider orientation="left">Usuários presentes no processo</Divider>
            <Table
              columns={columns}
              dataSource={usersProcess}
              loading={loading}
              rowKey={record => record.id}
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default ProcessDetail;
