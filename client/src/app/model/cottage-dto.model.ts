import {AddressDTO} from './address-dto.model';
import {RoomDTO} from './room-dto.model';
import {ReservationDTO} from './reservation-dto.model';

export class CottageDTO {
  public id!: number;
  public cottageOwnerId: number;
  public cottageName: string;
  public description: string;
  public photos: string[];
  public maxNumberOfGuests: number;
  public pricePerDay: number;
  public address: AddressDTO;
  public rooms: RoomDTO[];
  public reservations: ReservationDTO[];


  constructor(id: number, cottageName: string, description: string, photos: string[], cottageOwnerId: number,
              maxNumberOfGuests: number, pricePerDay: number,
              addressDTO: AddressDTO, roomsDTO: RoomDTO[],
              reservationsDTO: ReservationDTO[]) {
    this.id = id;
    this.cottageOwnerId = cottageOwnerId;
    this.cottageName = cottageName;
    this.description = description;
    this.photos = photos;
    this.maxNumberOfGuests = maxNumberOfGuests;
    this.pricePerDay = pricePerDay;
    this.address = addressDTO;
    this.rooms = roomsDTO;
    this.reservations = reservationsDTO;
  }
}
