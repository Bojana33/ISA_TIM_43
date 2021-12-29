export class PeriodDto {
  public id: number;
  public startDate: Date;
  public endDate: Date;


  constructor(id: number, startDate: string, endDate: string) {
    this.id = id;
    this.startDate = new Date();
    this.endDate = new Date();
  }
}
