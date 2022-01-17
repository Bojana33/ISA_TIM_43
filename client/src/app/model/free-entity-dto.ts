import {AddressDTO} from './address-dto.model';

export class FreeEntityDTO {
  public id!: number;
  public type!: string;
  public startDate!: Date;
  public endDate!: Date;
  public numberOfGuests!: number;
  public grade!: number;
  public country!: string;
  public city!: string;
  //public address: AddressDTO = new AddressDTO('', '', '', '');
}

