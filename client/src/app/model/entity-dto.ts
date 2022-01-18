

export class EntityDTO {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public pricePerDay: any,
    public maxNumberOfGuests: any
  ) {
  }
}
