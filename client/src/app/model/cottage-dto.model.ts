import {AddressDTO} from './address-dto.model';
import {RoomDTO} from './room-dto.model';
import {ReservationDTO} from './reservation-dto.model';

export class CottageDTO {
  public id!: number;
  public cottageName: string;
  public description: string;
  public photos: string[];
  public maxNumberOfGuests: number;
  public pricePerDay: number;
  public addressDTO: AddressDTO;
  public roomsDTO: RoomDTO[];
  public reservationsDTO: ReservationDTO[];


  constructor(id: number, cottageName: string, description: string, photos: string[],
              maxNumberOfGuests: number, pricePerDay: number,
              addressDTO: AddressDTO, roomsDTO: RoomDTO[],
              reservationsDTO: ReservationDTO[]) {
    this.id = id;
    this.cottageName = cottageName;
    this.description = description;
    this.photos = photos;
    this.maxNumberOfGuests = maxNumberOfGuests;
    this.pricePerDay = pricePerDay;
    this.addressDTO = addressDTO;
    this.roomsDTO = roomsDTO;
    this.reservationsDTO = reservationsDTO;
  }
}
