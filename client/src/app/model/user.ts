import {Address} from './address';

export class User {
  public id!: string;
  public firstName!: string;
  public email!: string;
  public surname!: string;
  public password!: string;
  public phoneNumber!: string;
  public addressDTO: Address = new Address();
  public firstLogin!: boolean;

}
