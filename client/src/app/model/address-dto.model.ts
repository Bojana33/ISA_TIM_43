export class AddressDTO {
  private city: string;
  private country: string;
  private street: string;
  private houseNumber: string;


  constructor(city: string, country: string, street: string, houseNumber: string) {
    this.city = city;
    this.country = country;
    this.street = street;
    this.houseNumber = houseNumber;
  }
}
