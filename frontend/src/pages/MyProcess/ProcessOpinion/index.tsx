import React, { useCallback, useState, useEffect } from 'react';
import {
  Button,
  Row,
  Col,
  Card,
  Divider,
  Form,
  Input,
  notification,
} from 'antd';
import { DoubleLeftOutlined, SaveOutlined } from '@ant-design/icons';
import { useHistory, useParams } from 'react-router-dom';
import { useForm } from 'antd/lib/form/Form';
import IProcess from '../../../interfaces/process';
import api from '../../../services/api';
import IProcessOpinion from '../../../interfaces/processOpinion';
import ProcessInfo from '../../../components/ProcessInfo';

const ProcessOpinion: React.FC = () => {
  const history = useHistory();
  const { id } = useParams();
  const [form] = useForm();
  const [process, setProcess] = useState<IProcess>();

  const loadProcess = useCallback(async () => {
    const response = await api.get(`/process/${id}`);
    setProcess(response.data.data);
  }, [id]);

  const back = useCallback(() => {
    history.goBack();
  }, [history]);

  const onSubmit = useCallback(async () => {
    const data: IProcessOpinion = {
      process: id,
      opinion: form.getFieldValue('opinion'),
    };

    try {
      await api.put('/user-process', data);
      notification.success({
        message: 'Sucesso',
        description: 'Parecer adicionado ao processo!',
        duration: 2,
        onClose: () => {
          back();
        },
      });
      loadProcess();
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
  }, [form, id, loadProcess, back]);

  useEffect(() => {
    loadProcess();
  }, [id, loadProcess]);

  return (
    <div className="process-page">
      <div className="page-header">
        <h2>Parecer do Processo</h2>
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
            <Divider orientation="left">Adicionar Parecer</Divider>
            <Form layout="vertical" form={form} onFinish={onSubmit}>
              <Form.Item
                label="Parecer:"
                name="opinion"
                rules={[
                  { required: true, message: 'Este campo é obrigatório!' },
                ]}
              >
                <Input.TextArea rows={5} />
              </Form.Item>
              <Form.Item>
                <Button
                  htmlType="submit"
                  type="primary"
                  icon={<SaveOutlined />}
                >
                  Salvar
                </Button>
              </Form.Item>
            </Form>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default ProcessOpinion;
