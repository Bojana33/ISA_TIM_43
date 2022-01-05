import {Address} from './address';
import {AddressDTO} from './address-dto.model';

export class UserDTO {
  public firstName!: string;
  public email!: string;
  public surname!: string;
  public password!: string;
  public phoneNumber!: string;
  public address: AddressDTO = ({} as any);
}
