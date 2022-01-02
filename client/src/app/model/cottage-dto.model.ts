import {AddressDTO} from './address-dto.model';
import {RoomDTO} from './room-dto.model';
import {ReservationDTO} from './reservation-dto.model';

export class CottageDTO {
  constructor(
    public id: number = 0,
    public cottageOwnerId: number = 0,
    public cottageName: string = '',
    public description: string = '',
    public photos: string[] = [],
    public maxNumberOfGuests: number = 0,
    public pricePerDay: number = 0,
    public address: AddressDTO = new AddressDTO('', '', '', ''),
    public rooms: RoomDTO[] = [],
    public reservations: ReservationDTO[] = [],
    public entityPhoto: string = ''
  ) {}
}
