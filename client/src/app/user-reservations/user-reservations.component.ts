import { Component, OnInit } from '@angular/core';
import {ReservationDTO} from "../model/reservation-dto.model";
import {ComplaintService} from "../service/complaint.service";
import { ReservationService } from '../service/reservation.service';
import { UserService } from '../service/user.service';
import {DatePipe} from "@angular/common";
import { EntityService } from '../service/entity.service';
import {MatDialog} from "@angular/material/dialog";
import {FastReservationDialogComponent} from "../fast-reservation/fast-reservation-dialog/fast-reservation-dialog.component";
import { FeedbackDialogComponent } from './feedback-dialog/feedback-dialog.component';
import {ReviewDialogComponent} from "./review-dialog/review-dialog.component";
import { OwnerService } from '../service/owner.service';

export interface FeedbackDialog {
  grade: any;
  description: any;
  complain: any;
  reservationDTO: ReservationDTO;
}

@Component({
  selector: 'app-user-reservations',
  templateUrl: './user-reservations.component.html',
  styleUrls: ['./user-reservations.component.css']
})
export class UserReservationsComponent implements OnInit {

  displayedColumns: string[] = ['Entity',  'Price','Start date', 'End date', 'Complain','Rate'];
  dataSource: Array<ReservationDTO> = [];
  entityName: Array<string> = [];
  owner!: any;
  userId!: number;
  oneName!: string;
  tmpReservations: Array<ReservationDTO> = [];

  constructor(private reservationService : ReservationService,
              private userService : UserService,
              private entityService: EntityService,
              private ownerService: OwnerService,
              private dialog: MatDialog) {

  }

  ngOnInit(): void {
    this.getUserId();
    //this.entityService.getEntity()
    //this.getData();
  }

  getData(userId: number) {
    this.reservationService.getAllUserReservations(this.userId).subscribe(
      res => {
        this.dataSource = res;
        for(let oneReservation of res) {
          this.entityService.getEntity(oneReservation.entityId).subscribe(res => {
            oneReservation.entityName = res.name;
            oneReservation.ownerName = res.owner.firstName;
            this.dataSource.push(oneReservation);
          });
        }
      });
  }

  getName(id: number){
    this.entityService.getEntity(id).subscribe(res => {
      this.oneName = res.name;
    });
  }

  getUserId(): void{
    this.userService.getMyInfo().subscribe((response) => {
      this.userId = response.id;
      this.getData(response.id);
    });
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  writeComplainment(row: any){
    this.openReservationDialog(row);
  }

  openReservationDialog(row: any): void {
    const dialogRef = this.dialog.open(FeedbackDialogComponent, {
      width: '250px',
      data: {reservationDTO: row, complain: '', grade: undefined, description: undefined},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  giveReview(row:any) {
    const dialogRef = this.dialog.open(ReviewDialogComponent, {
      width: '250px',
      data: {reservationDTO: row, complain: '', grade: undefined, description: undefined},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
