import React, { useState, useEffect, useCallback } from 'react';
import {
  Button,
  Card,
  Row,
  Col,
  Form,
  Select,
  Divider,
  Table,
  notification,
} from 'antd';
import { useParams, useHistory } from 'react-router-dom';
import { DoubleLeftOutlined } from '@ant-design/icons';
import { ColumnsType } from 'antd/lib/table';
import { useForm } from 'antd/lib/form/Form';
import IProcess from '../../../interfaces/process';
import api from '../../../services/api';

import './styles.scss';
import IUser from '../../../interfaces/user';
import ProcessInfo from '../../../components/ProcessInfo';

const { Option } = Select;

interface IUserProcess {
  user: string;
  process: string;
}

const ProcessDesignate: React.FC = () => {
  const [process, setProcess] = useState<IProcess>();
  const [form] = useForm();
  const [usersProcess, setUsersProcess] = useState<IUser[]>();
  const [users, setUsers] = useState<IUser[]>();
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

  const filterUsers = useCallback(
    (userId: string) => {
      const userFind = usersProcess?.find(user => user.id === userId);
      if (userFind === undefined) {
        return false;
      }
      return true;
    },
    [usersProcess],
  );

  const onSubmit = useCallback(async () => {
    const data: IUserProcess = {
      process: id,
      user: form.getFieldValue('user'),
    };

    try {
      await api.post('/user-process', data);
      notification.success({
        message: 'Sucesso',
        description: 'Usuário adicionado ao processo!',
        duration: 2,
      });
      loadProcess();
      form.setFieldsValue({ user: '' });
    } catch (err) {
      if (err.response !== undefined) {
        err.response.data.errors.map((erro: string) => {
          notification.warning({
            message: 'Alerta',
            description: erro,
          });
        });
      } else {
        notification.error({
          message: 'Erro',
          description: 'Falha no servidor!',
        });
      }
    }
  }, [form, id, loadProcess]);

  useEffect(() => {
    async function loadUsersFinishers() {
      const response = await api.get(`/users/finishers`);
      setUsers(response.data.data);
    }
    loadProcess();
    loadUsersFinishers();
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
        <h2>Designar Processo</h2>
        <Button type="primary" icon={<DoubleLeftOutlined />} onClick={back}>
          Voltar
        </Button>
      </div>
      <Row>
        <Col lg={12} xs={24}>
          <Card>
            <ProcessInfo process={process} />
          </Card>
        </Col>
        <Col lg={12} xs={24}>
          <Card>
            <Divider orientation="left">Adicionar Usuário</Divider>
            <Form layout="vertical" form={form} onFinish={onSubmit}>
              <Form.Item
                label="Usuário:"
                name="user"
                rules={[{ required: true, message: 'Selecione um usuário!' }]}
              >
                <Select allowClear placeholder="Pesquisar usuário">
                  {users?.map(user => {
                    return (
                      <Option
                        key={user.id}
                        value={user.id}
                        title={user.id}
                        disabled={filterUsers(user.id)}
                      >
                        {user.name}
                      </Option>
                    );
                  })}
                </Select>
              </Form.Item>
              <Form.Item>
                <Button htmlType="submit" type="primary">
                  Designar
                </Button>
              </Form.Item>
            </Form>
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

export default ProcessDesignate;
