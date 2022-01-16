import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ReservationDTO} from '../../model/reservation-dto.model';
import {DialogData} from '../client-reservations.component';
import {PeriodDTO} from '../../model/period-dto.model';
import {EntityService} from '../../service/entity.service';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.css']
})
export class ReservationDialogComponent implements OnInit {

  period!: any;
  reservation!: any;

  constructor(
    public dialogRef: MatDialogRef<ReservationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private entityService: EntityService,
    private snackBar: MatSnackBar,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  createReservation(){
    this.period = new PeriodDTO(0, this.data.reservationStartDate, this.data.reservationEndDate);
    this.reservation = new ReservationDTO();
    this.reservation.numberOfGuests = this.data.numberOfGuests;
    this.reservation.reservedPeriod = this.period;
    this.reservation.additionalServices = this.data.additionalServices;
    this.reservation.entityId = this.data.entityId;
    this.reservation.clientId = this.data.clientId;
    console.log(this.reservation.clientId);
    this.entityService.reserve(this.reservation).subscribe(
      res => {
        console.log(res);
      }
    );
    this.dialogRef.close();
    this.snackBar.open('Successfully booked, check your email.', 'cancel');
  }
}
