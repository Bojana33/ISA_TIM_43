export class PeriodDto {
  private id: number;
  private startDate: string;
  private endDate: string;


  constructor(id: number, startDate: string, endDate: string) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
