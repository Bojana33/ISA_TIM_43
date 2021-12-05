import { AdditionalService } from "./additional-service";
import { Address } from "./address";

export class Adventure{
    constructor(
      public id: number,
      public name: string,
      public address: Address, //= new Address('','','',''),
      public description: string,
      public instructorBio: string,
      public photos: string[],
      public maxNumberOfGuests: number,
      public houseRules: string,
      //public additionalServices: AdditionalService,
      public pricePerDay: number,
      public cancellationFee: number,
      public entityPhoto: string,
      public defaultFishingEquipment: string
    ){}
  }