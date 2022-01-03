import {Address} from './address';

export class Cottage{
  constructor(
    public id: number,
    public name: string,
    public averageGrade: number,
    public address: Address, //= new Address('','','',''),
    public description: string,
    public instructorBio: string,
    public photos: string[],
    public maxNumberOfGuests: number,
    public houseRules: string,
    public pricePerDay: number,
    public cancellationFee: number,
    public entityPhoto: string,
    public defaultFishingEquipment: string
  ){}
}
