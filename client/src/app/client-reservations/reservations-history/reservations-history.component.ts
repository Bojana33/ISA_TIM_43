import { Component, OnInit } from '@angular/core';
import {ReservationDTO} from "../../model/reservation-dto.model";
import {ClientService} from "../../service/client.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-reservations-history',
  templateUrl: './reservations-history.component.html',
  styleUrls: ['./reservations-history.component.css']
})
export class ReservationsHistoryComponent implements OnInit {

  displayedColumns: string[] = ['Reservation start date', 'Reservation end date',
    'Price', 'Number of guests'];
  dataSource: Array<ReservationDTO> = [];
  canceledEnabled!: boolean;
  dateAdded: Date = new Date();
  selected: boolean = false;
  sortedData: Array<ReservationDTO> = [];

  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.clientService.getReservationsHistory('/all').subscribe(res => {
      this.dataSource = res;
      console.log(res);
    });
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  select(criterion:any){
    this.selected = true;
    if (criterion === 'adventure')
      this.clientService.getReservationsHistory('/adventure').subscribe(res => {
        this.dataSource = res;
      });
    else if (criterion === 'boat')
      this.clientService.getReservationsHistory('/boat').subscribe(res => {
        this.dataSource = res;
      });
    else if (criterion === 'cottage')
      this.clientService.getReservationsHistory('/cottage').subscribe(res => {
        this.dataSource = res;
      });
  }

  sort(criterion: any) {
    if(criterion === 'startDate') {
      this.sortedData = this.dataSource.sort((a, b) => a.reservedPeriod.startDate > b.reservedPeriod.startDate ? 1 : -1);
      console.log(this.dataSource);
    }
    else if(criterion === 'endDate') {
      this.sortedData = this.dataSource.sort((a, b) => a.reservedPeriod.endDate > b.reservedPeriod.endDate ? 1 : -1);
      console.log(this.dataSource);
    }
    else if(criterion === 'price') {
      this.sortedData = this.dataSource.sort((a, b) => a.price > b.price ? 1 : -1);
      console.log(this.dataSource);
    }
    //else if(criterion === 'duration')
      //this.dataSource.sort((a, b) => (a.reservedPeriod.endDate.getTime() - a.reservedPeriod.startDate) > (b.reservedPeriod.endDate - b.reservedPeriod.startDate) ? 1 : -1);
  }
}
