import {PeriodDTO} from './period-dto.model';
import {AdditionalServicesDTO} from './AdditionalServicesModel/additional-services-dto.model';
import {ReservationStatus} from '../enum/ReservationStatus';

export class ReservationDTO {
  public id!: number;
  public price!: number;
  public numberOfGuests!: number;
  public creationDate!: Date;
  public reservationStatus!: ReservationStatus;
  public additionalNotes!: string;
  public reservedPeriod!: PeriodDTO;
  public salePeriod!: PeriodDTO;
  public additionalServices!: AdditionalServicesDTO[];
  public entityId!: number;
  public clientId!: number;
  public discount: number = 0;
  public ownersIncome!: number;
  public entityName!: string;
  public ownerName!: string;
  public description!: string;
  public pricePerDay!: number;

  //constructor(
    //public id: number,
    //public price: number,
    //public numberOfGuests: number,
    //public creationDate: Date,
    //public reservationStatus: ReservationStatus,
    //public additionalNotes: string,
    //public reservedPeriod: PeriodDto,
    //public salePeriod: PeriodDto,
    //public additionalServices: AdditionalServicesDTO[],
    //public entityId: number,
    //public clientId: number
//) {
  //}
}
