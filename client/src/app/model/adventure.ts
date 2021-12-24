import { AdditionalService } from "./additional-service";
import { Address } from "./address";

export class Adventure{
      public id!: number;
      public adventureOwnerId!: number;
      public name!: string;
      public addressDTO: Address = new Address();
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
  }