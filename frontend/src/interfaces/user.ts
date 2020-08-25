import { UserTypes } from '../enums/enums';

export default interface IUser {
  id: string;
  name: string;
  email: string;
  userType: UserTypes;
}
