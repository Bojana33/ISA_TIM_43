import {Address} from './address';
import {AdditionalService} from './additional-service';
import {AddressDTO} from './address-dto.model';
import {RoomDTO} from "./room-dto.model";
import {ReservationDTO} from "./reservation-dto.model";

export class AdventureDTO{
  public id!: number;
  public adventureOwnerId!: number;
  public name!: string;
  public addressDTO: AddressDTO;
  public description!: string;
  public instructorBio!: string;
  public photos: string[] = [];
  public maxNumberOfGuests!: number;
  public houseRules!: string;
  public additionalServicesDto!: AdditionalService;
  public pricePerDay!: number;
  public cancellationFee!: number;
  public entityPhoto!: string;
  public defaultFishingEquipment!: string;
  public reservations: ReservationDTO[];

  constructor(id: number, name: string, description: string, photos: string[], adventureOwnerId: number,
              maxNumberOfGuests: number, pricePerDay: number,
              addressDTO: AddressDTO, reservationsDTO: ReservationDTO[]) {
    this.id = id;
    this.adventureOwnerId = adventureOwnerId;
    this.name = name;
    this.description = description;
    this.photos = photos;
    this.maxNumberOfGuests = maxNumberOfGuests;
    this.pricePerDay = pricePerDay;
    this.addressDTO = addressDTO;
    this.reservations = reservationsDTO;
  }
}
