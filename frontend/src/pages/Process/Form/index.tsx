import React, { useCallback } from 'react';
import { Button, Card, Form, Input, Space, notification } from 'antd';
import {
  DoubleLeftOutlined,
  SaveOutlined,
  CloseOutlined,
} from '@ant-design/icons';
import { useHistory } from 'react-router-dom';
import { useForm } from 'antd/lib/form/Form';
import api from '../../../services/api';

const ProcessForm: React.FC = () => {
  const history = useHistory();
  const [form] = useForm();

  const back = useCallback(() => {
    history.goBack();
  }, [history]);

  const onSave = useCallback(async () => {
    try {
      const data = form.getFieldsValue();
      await api.post('/process', data);
      notification.success({
        message: 'Sucesso',
        description: 'Processo cadastrado com sucesso!',
        onClose: () => {
          back();
        },
      });
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
  }, [form, back]);

  return (
    <div className="users-page">
      <div className="page-header">
        <h2>Novo Processo</h2>
        <Button type="primary" icon={<DoubleLeftOutlined />} onClick={back}>
          Voltar
        </Button>
      </div>
      <Card>
        <Form form={form} layout="vertical" onFinish={onSave}>
          <Form.Item
            label="Número do Processo:"
            name="process"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Vara:"
            name="stick"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Advogado de Defesa:"
            name="lawyer"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Valor do Processo:"
            name="value"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input type="number" step="any" />
          </Form.Item>
          <Form.Item
            label="Tipo do Processo:"
            name="processType"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Descrição:"
            name="subject"
            rules={[{ required: true, message: 'Preencha este campo!' }]}
          >
            <Input.TextArea />
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

export default ProcessForm;
