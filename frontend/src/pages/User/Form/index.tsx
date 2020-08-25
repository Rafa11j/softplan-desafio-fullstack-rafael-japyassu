import React, { useCallback, useEffect } from 'react';
import { Button, Card, Form, Input, Select, Space, notification } from 'antd';
import {
  DoubleLeftOutlined,
  SaveOutlined,
  CloseOutlined,
} from '@ant-design/icons';
import { useHistory, useParams } from 'react-router-dom';
import { useForm } from 'antd/lib/form/Form';
import { UserTypes } from '../../../enums/enums';
import api from '../../../services/api';

const { Option } = Select;

const UserForm: React.FC = () => {
  const history = useHistory();
  const { id } = useParams();
  const [form] = useForm();

  const back = useCallback(() => {
    history.goBack();
  }, [history]);

  const onSave = useCallback(async () => {
    try {
      const data = form.getFieldsValue();
      if (id !== undefined) {
        await api.put(`/users/${id}`, data);
        notification.success({
          message: 'Sucesso',
          description: 'Usuário atualizado com sucesso!',
          onClose: () => {
            back();
          },
        });
      } else {
        await api.post('/users', data);
        notification.success({
          message: 'Sucesso',
          description: 'Usuário cadastrado com sucesso!',
          onClose: () => {
            back();
          },
        });
      }
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
  }, [form, back, id]);

  useEffect(() => {
    async function loadUser() {
      const response = await api.get(`/users/${id}`);
      form.setFieldsValue({
        name: response.data.data.name,
        email: response.data.data.email,
        userType: response.data.data.userType,
      });
    }

    if (id !== undefined) {
      loadUser();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  return (
    <div className="users-page">
      <div className="page-header">
        <h2>Novo Usuário</h2>
        <Button type="primary" icon={<DoubleLeftOutlined />} onClick={back}>
          Voltar
        </Button>
      </div>
      <Card>
        <Form form={form} layout="vertical" onFinish={onSave}>
          <Form.Item
            label="Nome:"
            name="name"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="E-mail:"
            name="email"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          {id === undefined && (
            <Form.Item
              label="Senha:"
              name="password"
              rules={[{ required: true, message: 'Preencha este campo!' }]}
            >
              <Input.Password />
            </Form.Item>
          )}
          <Form.Item
            label="Tipo do Usuário:"
            name="userType"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Select>
              <Option value={UserTypes.ADMINISTRATOR}>Administrador</Option>
              <Option value={UserTypes.TRIATOR}>Triador</Option>
              <Option value={UserTypes.FINISHER}>Finalizador</Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Space size="middle">
              <Button type="primary" htmlType="submit" icon={<SaveOutlined />}>
                Salvar
              </Button>
              <Button htmlType="button" onClick={back} icon={<CloseOutlined />}>
                Cancelar
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default UserForm;
