import React from 'react';
import { Descriptions, Tag, Badge } from 'antd';
import formatValue from '../../utils/formatValue';
import { ProcessState } from '../../enums/enums';
import formatDate from '../../utils/formatDate';
import IProcess from '../../interfaces/process';

interface IProps {
  process?: IProcess;
}

const ProcessInfo: React.FC<IProps> = props => {
  const { process } = props;

  return (
    <Descriptions title={`Dados do Processo #${process?.process}`} bordered>
      <Descriptions.Item label="Número do Processo">
        {process?.process}
      </Descriptions.Item>
      <Descriptions.Item label="Vara">{process?.stick}</Descriptions.Item>
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
              process?.state === ProcessState.OPEN ? 'Aberto' : 'Em Andamento'
            }
          />
        )}
      </Descriptions.Item>
      <Descriptions.Item label="Descrição" span={3}>
        {process?.subject}
      </Descriptions.Item>
      {process?.state === ProcessState.FINISHED && (
        <Descriptions.Item label="Parecer" span={3}>
          {process?.opinion}
        </Descriptions.Item>
      )}
      <Descriptions.Item label="Data de Cadastro">
        {formatDate(process?.createdAt)}
      </Descriptions.Item>
      <Descriptions.Item label="Última atualização">
        {formatDate(process?.updatedAt)}
      </Descriptions.Item>
    </Descriptions>
  );
};

export default ProcessInfo;
