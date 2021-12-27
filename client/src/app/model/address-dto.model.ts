export class AddressDTO {
  city: string;
  country: string;
  street: string;
  houseNumber: string;


  constructor(city: string, country: string, street: string, houseNumber: string) {
    this.city = city;
    this.country = country;
    this.street = street;
    this.houseNumber = houseNumber;
  }
}
