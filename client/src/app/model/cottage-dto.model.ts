import {AddressDTO} from './address-dto.model';
import {RoomDTO} from './room-dto.model';
import {ReservationDTO} from './reservation-dto.model';
import {AdditionalServicesDTO} from "./AdditionalServicesModel/additional-services-dto.model";

export class CottageDTO {
    public id!: number;
    public cottageOwnerId!: number;
    public cottageName!: string;
    public description!: string;
    public photos!: string[];
    public maxNumberOfGuests!: number;
    public pricePerDay!: number;
    public address!: AddressDTO;
    public rooms!: RoomDTO[];
    public reservations!: ReservationDTO[];
    public entityPhoto!: string;
    public averageGrade!: number;
    public additionalServices!: AdditionalServicesDTO[];
}
