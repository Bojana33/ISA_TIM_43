export class RoomDTO {
  id: number;
  numberOfBeds: number;


  constructor(id: number, numberOfBeds: number) {
    this.id = id;
    this.numberOfBeds = numberOfBeds;
  }
}
