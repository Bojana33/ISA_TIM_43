import { Component, OnInit } from '@angular/core';
import {ReservationDTO} from "../../model/reservation-dto.model";
import {ClientService} from "../../service/client.service";
import {DatePipe} from "@angular/common";
import {ReservationService} from "../../service/reservation.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-future-reservations',
  templateUrl: './future-reservations.component.html',
  styleUrls: ['./future-reservations.component.css']
})
export class FutureReservationsComponent implements OnInit {

  displayedColumns: string[] = ['Reservation start date', 'Reservation end date',
    'Price', 'Number of guests', 'Cancel'];
  dataSource: Array<ReservationDTO> = [];
  canceledEnabled!: boolean;
  dateAdded: Date = new Date();

  constructor(private clientService: ClientService,
              private reservationService: ReservationService,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.setDateToCompare();
    this.clientService.getFutureReservations().subscribe(res => {
      this.dataSource = res;
      console.log('future reservations', res);
    });
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  cancel(element: any) {
    this.reservationService.cancelReservation(element.id).subscribe(res =>{
      console.log(res);
      this.snackBar.open('Successfully canceled.', 'cancel');
    },
    err => {
      this.snackBar.open('Successfully canceled. Reload page', 'cancel');
    });
  }

  setCancelEnabled(element: any){
    let d = new Date();
    if(element.reservedPeriod.startDate < d.getTime())
      return false;
    else
      return true;
  }

  setDateToCompare(){
    let d = new Date();
    this.dateAdded.setDate(d.getDate() + 3);
  }
}
