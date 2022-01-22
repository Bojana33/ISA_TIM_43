import { AdditionalService } from "./additional-service";
import { AdditionalServicesDTO } from "./AdditionalServicesModel/additional-services-dto.model";
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
      public additionalServices!: AdditionalServicesDTO[];
      public pricePerDay!: number;
      public cancellationFee!: number;
      public entityPhoto!: string;
      public defaultFishingEquipment!: string;
      public averageGrade!: number;
  }