import {PeriodDto} from './period-dto.model';
import {AdditionalServicesDTO} from './AdditionalServicesModel/additional-services-dto.model';
import {ReservationStatus} from '../enum/ReservationStatus';

export class ReservationDTO {
  constructor(
    public id: number,
    public price: number,
    public numberOfGuests: number,
    public creationDate: Date,
    public reservationStatus: ReservationStatus,
    public additionalNotes: string,
    public reservedPeriod: PeriodDto,
    public salePeriod: PeriodDto,
    public additionalServices: AdditionalServicesDTO[],
    public entityId: number,
) {
  }
}
