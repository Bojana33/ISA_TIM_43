import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FeedbackDialog, UserReservationsComponent} from "../user-reservations.component";
import {EntityService} from "../../service/entity.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DatePipe} from "@angular/common";
import {ClientsReview} from "../../model/clients-review";
import {ClientsReviewService} from "../../service/clients-review.service";

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.css']
})
export class ReviewDialogComponent implements OnInit {
  clientReview: ClientsReview = new ClientsReview();
  constructor(public dialogRef: MatDialogRef<UserReservationsComponent>,
              @Inject(MAT_DIALOG_DATA) public data: FeedbackDialog,
              private entityService: EntityService,
              private snackBar: MatSnackBar,
              private clientReviewService: ClientsReviewService) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  createReview(){
    this.clientReview.grade = this.data.grade;
    this.clientReview.description = this.data.description;
    this.clientReview.reservationDTO = this.data.reservationDTO;
  }

  sendReview() {
    this.createReview();
    this.clientReviewService.createReview(this.clientReview).subscribe(res => {
      console.log(res);
      this.snackBar.open('Review successfully sent', 'cancel');
    });
    this.dialogRef.close();
  }
}
