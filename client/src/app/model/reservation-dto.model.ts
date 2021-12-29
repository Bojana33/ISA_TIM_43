import {PeriodDto} from './period-dto.model';
import {AdditionalServicesDTO} from './AdditionalServicesModel/additional-services-dto.model';
import {ReservationStatus} from '../enum/ReservationStatus';

export class ReservationDTO {
  public id: number;
  public price: number;
  public numberOfGuests: number;
  public creationDate: Date;
  public reservationStatus: ReservationStatus;
  public additionalNotes: string;
  public reservedPeriod: PeriodDto;
  public salePeriod: PeriodDto;
  public additionalServices: AdditionalServicesDTO[];
  public entityId: number;

  constructor(id: number, price: number, numberOfGuests: number, creationDate: Date, reservationStatus: ReservationStatus,
              additionalNotes: string, reservedPeriod: PeriodDto, salePeriod: PeriodDto, additionalServices: AdditionalServicesDTO[],
              entityId: number) {
    this.id = id;
    this.price = price;
    this.numberOfGuests = numberOfGuests;
    this.creationDate = creationDate;
    this.reservationStatus = reservationStatus;
    this.additionalNotes = additionalNotes;
    this.reservedPeriod = reservedPeriod;
    this.salePeriod = salePeriod;
    this.additionalServices = additionalServices;
    this.entityId = entityId;
  }
}
