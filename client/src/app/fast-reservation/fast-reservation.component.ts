import { Component, OnInit } from '@angular/core';
import {ReservationService} from '../service/reservation.service';
import {ReservationDTO} from '../model/reservation-dto.model';
import {DatePipe} from "@angular/common";
import {ReservationDialogComponent} from "../client-reservations/reservation-dialog/reservation-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import { UserService } from '../service/user.service';
import { FastReservationDialogComponent } from './fast-reservation-dialog/fast-reservation-dialog.component';

@Component({
  selector: 'app-fast-reservation',
  templateUrl: './fast-reservation.component.html',
  styleUrls: ['./fast-reservation.component.css']
})
export class FastReservationComponent implements OnInit {

  displayedColumns: string[] = ['Sale start date', 'Sale end date', 'Reservation start date', 'Reservation end date',
                                'Price', 'Discount', 'Reserve'];
  dataSource: Array<ReservationDTO> = [];
  userId: any;
  loaded!: boolean;

  constructor(private reservationService: ReservationService, private dialog: MatDialog, private userService: UserService) {
    this.loaded = false;
  }

  ngOnInit(): void {
      this.reservationService.getFutureReservationsOnSale().subscribe(
        res => {
          this.loaded = true;
          console.log(res);
          this.dataSource = res;}
      );
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  reserve(row: any) {
    console.log(row);
    this.userService.getMyInfo().subscribe((response) =>
      this.userId = response);
    this.openReservationDialog(row);
  }

  openReservationDialog(row: any): void {
    const dialogRef = this.dialog.open(FastReservationDialogComponent, {
      width: '250px',
      data: {id: row.id, entityId: row.entityId, reservationStartDate: row.reservedPeriod.startDate, reservationEndDate: row.reservedPeriod.endDate, additionalServices: row.additionalServices, clientId: this.userId.id},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
