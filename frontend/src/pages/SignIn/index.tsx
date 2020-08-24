import React, { useCallback } from 'react';
import { Card, Button, Form, Input, notification } from 'antd';
import { LoginOutlined } from '@ant-design/icons';
import { Store } from 'antd/lib/form/interface';

import { useHistory } from 'react-router-dom';
import { useAuth } from '../../hooks/auth';

import './styles.scss';
import IAuth from '../../interfaces/auth';

const { useForm } = Form;

const SignIn: React.FC = () => {
  const { signIn } = useAuth();
  const [form] = useForm();
  const history = useHistory();

  const onSignIn = useCallback(
    async (data: Store) => {
      try {
        const auth = data as IAuth;
        await signIn({ email: auth.email, password: auth.password });
        history.push('/inicio');
      } catch (err) {
        if (err.response !== undefined) {
          notification.warning({
            message: 'Alerta',
            description: 'Credenciais inválidas!',
          });
        } else {
          notification.error({
            message: 'Erro',
            description: 'Falha no servidor!',
          });
        }
      }
    },
    [signIn, history],
  );

  return (
    <div id="signin-page">
      <Card className="signin-card">
        <h2>ADVSoft - Login</h2>
        <Form
          layout="vertical"
          hideRequiredMark
          form={form}
          onFinish={onSignIn}
        >
          <Form.Item
            label="E-mail"
            name="email"
            rules={[{ required: true, message: 'Informe seu e-mail!' }]}
          >
            <Input type="email" />
          </Form.Item>

          <Form.Item
            label="Senha"
            name="password"
            rules={[{ required: true, message: 'Informe sua senha!' }]}
          >
            <Input.Password />
          </Form.Item>
          <Button
            type="primary"
            className="btn-signin"
            htmlType="submit"
            icon={<LoginOutlined />}
          >
            Entrar
          </Button>
        </Form>
      </Card>
    </div>
  );
};

export default SignIn;
