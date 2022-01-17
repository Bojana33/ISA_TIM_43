import { ReservationDTO } from './reservation-dto.model';
export class UserComplaint {
  public id!: number;
  public description!: string;
  public response!: string;
  public processed!: boolean;
  public reservationDTO!: ReservationDTO;
}
