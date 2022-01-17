import { ClientsReviewStatus } from './../enum/clients-review-status.enum';
import { ReservationDTO } from "./reservation-dto.model";

export class ClientsReview {
    public id!: number;
    public description!: string;
    public grade!: number;
    public status!: ClientsReviewStatus;
    public reservationDTO!: ReservationDTO;
}
