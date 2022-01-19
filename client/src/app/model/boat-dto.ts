import {AddressDTO} from './address-dto.model';
import {ReservationDTO} from './reservation-dto.model';
import {BoatTypeEnum} from '../enum/boat-type.enum';

export class BoatDTO {
  constructor(
    public id: number = 0,
    public boatOwnerId: number = 0,
    public name: string = '',
    public description: string = '',
    public type: BoatTypeEnum = BoatTypeEnum.YACHT,
    public length: number = 0,
    public enginePower: number = 0,
    public engineNumber: string = '',
    public fishingEquipment: string = '',
    public houseRules: string = '',
    public photos: string[] = [],
    public maxSpeed: number = 0,
    public averageGrade: number = 0,
    public capacity: number = 0,
    public cancellationFee: number = 0,
    public maxNumberOfGuests: number = 0,
    public pricePerDay: number = 0,
    public address: AddressDTO = new AddressDTO('', '', '', ''),
    public reservations: ReservationDTO[] = [],
    public entityPhoto: string = ''
  ) {}
}
