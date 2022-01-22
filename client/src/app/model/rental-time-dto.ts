export class RentalTimeDto {
  constructor(
    public id: number = 0,
    public startDate: Date = new Date(),
    public endDate: Date = new Date(),
    public entityId = 0){
  }
}

