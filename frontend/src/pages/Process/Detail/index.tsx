import React, { useState, useEffect, useCallback } from 'react';
import {
  Button,
  Descriptions,
  Card,
  Badge,
  Row,
  Col,
  Divider,
  Table,
  Tag,
} from 'antd';
import { useParams, useHistory } from 'react-router-dom';
import { DoubleLeftOutlined } from '@ant-design/icons';
import { ColumnsType } from 'antd/lib/table';
import IProcess from '../../../interfaces/process';
import api from '../../../services/api';
import formatValue from '../../../utils/formatValue';
import formatDate from '../../../utils/formatDate';

import IUser from '../../../interfaces/user';
import { ProcessState } from '../../../enums/enums';

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
            <Descriptions
              title={`Dados do Processo #${process?.process}`}
              bordered
            >
              <Descriptions.Item label="Número do Processo">
                {process?.process}
              </Descriptions.Item>
              <Descriptions.Item label="Vara">
                {process?.stick}
              </Descriptions.Item>
              <Descriptions.Item label="Valor">
                {formatValue(process?.value)}
              </Descriptions.Item>
              <Descriptions.Item label="Tipo de Processo">
                {process?.processType}
              </Descriptions.Item>
              <Descriptions.Item label="Advogado de Defesa" span={2}>
                {process?.lawyer}
              </Descriptions.Item>
              <Descriptions.Item label="Status" span={3}>
                {process?.state === ProcessState.FINISHED ? (
                  <Tag color="cyan">Finalizado</Tag>
                ) : (
                  <Badge
                    status="processing"
                    text={
                      process?.state === ProcessState.OPEN
                        ? 'Aberto'
                        : 'Em Andamento'
                    }
                  />
                )}
              </Descriptions.Item>
              <Descriptions.Item label="Descrição" span={3}>
                {process?.subject}
              </Descriptions.Item>
              <Descriptions.Item label="Parecer" span={3}>
                {process?.opinion}
              </Descriptions.Item>
              <Descriptions.Item label="Data de Cadastro">
                {formatDate(process?.createdAt)}
              </Descriptions.Item>
              <Descriptions.Item label="Última atualização">
                {formatDate(process?.updatedAt)}
              </Descriptions.Item>
            </Descriptions>
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
