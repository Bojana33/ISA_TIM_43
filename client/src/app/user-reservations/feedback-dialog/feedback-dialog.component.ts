import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../../client-reservations/client-reservations.component";
import {EntityService} from "../../service/entity.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FeedbackDialog, UserReservationsComponent} from "../user-reservations.component";
import {UserComplaint} from "../../model/user-complaint";
import {ComplaintService} from "../../service/complaint.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-feedback-dialog',
  templateUrl: './feedback-dialog.component.html',
  styleUrls: ['./feedback-dialog.component.css']
})
export class FeedbackDialogComponent implements OnInit {
  userComplaint: UserComplaint = new UserComplaint();
  constructor(
    public dialogRef: MatDialogRef<UserReservationsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: FeedbackDialog,
    private entityService: EntityService,
    private snackBar: MatSnackBar,
    private complaintService: ComplaintService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit(): void {
  }

  createFeedback(){
    this.userComplaint.description = this.data.complain;
    this.userComplaint.reservationDTO = this.data.reservationDTO;
  }

  sendFeedback(){
    this.createFeedback();
    console.log(this.userComplaint);
    this.complaintService.createComplaint(this.userComplaint).subscribe(res => {
      console.log(res);
    });
    this.dialogRef.close();
    this.snackBar.open('Complaint successfully sent', 'cancel');
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }
}
