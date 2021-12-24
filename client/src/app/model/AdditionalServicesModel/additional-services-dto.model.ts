export class AdditionalServicesDTO {
  private id: number;
  private name: string;
  private price: number;


  constructor(id: number, name: string, price: number) {
    this.id = id;
    this.name = name;
    this.price = price;
  }
}
