import {PeriodDto} from './period-dto.model';
import {AdditionalServicesDTO} from './AdditionalServicesModel/additional-services-dto.model';

export class ReservationDTO {
  private id: number;

  private price: number;

  private numberOfGuests: number;

  private additionalNotes: string;

  private reservedPeriod: PeriodDto;

  private salePeriod: PeriodDto;

  private additionalServices: AdditionalServicesDTO[];

  private entityId: number;


  constructor(id: number, price: number, numberOfGuests: number, additionalNotes: string,
              reservedPeriod: PeriodDto, salePeriod: PeriodDto, additionalServices: AdditionalServicesDTO[], entityId: number) {
    this.id = id;
    this.price = price;
    this.numberOfGuests = numberOfGuests;
    this.additionalNotes = additionalNotes;
    this.reservedPeriod = reservedPeriod;
    this.salePeriod = salePeriod;
    this.additionalServices = additionalServices;
    this.entityId = entityId;
  }
}
