import { ReservationDTO } from './reservation-dto.model';
import { ReviewStatus } from "../enum/review-status.enum";

export class OwnersReview {
    public id!: number;
    public description!: string;
    public appeared!: boolean;
    public isReported!: boolean;
    public reviewStatus!: ReviewStatus;
    public reservationDTO!: ReservationDTO;
    public clientId!: number;
}
