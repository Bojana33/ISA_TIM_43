import {Address} from './address';

export class Boat{
  public id!: number;
  public name!: string;
  public address: Address = new Address();
  public description!: string;
  public maxNumberOfGuests!: number;
  public pricePerDay!: number;
  public cancellationFee!: number;
  public averageGrade!: number;
  public entityPhoto!: string;
}
