import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../../client-reservations/client-reservations.component";
import {EntityService} from "../../service/entity.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {PeriodDTO} from "../../model/period-dto.model";
import {ReservationDTO} from "../../model/reservation-dto.model";

@Component({
  selector: 'app-fast-reservation-dialog',
  templateUrl: './fast-reservation-dialog.component.html',
  styleUrls: ['./fast-reservation-dialog.component.css']
})
export class FastReservationDialogComponent implements OnInit {

  period!: any;
  reservation!: any;
  success: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<FastReservationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private entityService: EntityService,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  createReservation(){
    this.period = new PeriodDTO(0, this.data.reservationStartDate, this.data.reservationEndDate);
    this.reservation = new ReservationDTO();
    this.reservation.id = this.data.id;
    this.reservation.numberOfGuests = this.data.numberOfGuests;
    this.reservation.reservedPeriod = this.period;
    this.reservation.additionalServices = this.data.additionalServices;
    this.reservation.entityId = this.data.entityId;
    this.reservation.clientId = this.data.clientId;
    console.log(this.reservation.clientId);
    this.entityService.fastReservation(this.reservation).subscribe(
      res => {
        console.log(res);
        if(res !== null) {
          this.snackBar.open('Successfully booked, check your email.', 'cancel');
          this.success = true;
        }
        this.checkSuccess();
      }
    );
    this.dialogRef.close();
  }

  checkSuccess(){
    if (this.success === false)
      this.snackBar.open('Something went wrong.', 'cancel');
  }
}
