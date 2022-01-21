import {ReviewStatus} from '../enum/review-status.enum';
import {ReservationDTO} from './reservation-dto.model';

export class OwnerReviewMinimalDto {
  constructor(
    public id: number = 0,
    public description: string = '',
    public appeared: boolean = false,
    public isReported: boolean = false,
    public reservationId: number = 0,
    public clientId: number = 0) {
  }
}
