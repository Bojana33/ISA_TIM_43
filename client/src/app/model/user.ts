import {Address} from './address';

export class User {
  public id!: string;
  public firstName!: string;
  public email!: string;
  public surname!: string;
  public password!: string;
  public phoneNumber!: string;
  public addressDTO: Address = new Address();

  // constructor(id: string, fistName: string, email: string, surname: string, password: string) {
  //   this.id = id;
  //   this.firstName = fistName;
  //   this.email = email;
  //   this.surname = surname;
  //   this.password = password;
  // }
}
