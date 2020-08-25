import { ProcessState } from '../enums/enums';

export default interface IProcess {
  id: string;
  process: string;
  stick: string;
  subject: string;
  value: number;
  lawyer: string;
  processType: string;
  opinion: string;
  state: ProcessState;
  createdAt: Date;
  updatedAt: Date;
}
