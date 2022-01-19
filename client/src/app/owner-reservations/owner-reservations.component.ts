import { User } from './../model/user';
import { OwnersReviewService } from './../service/owners-review.service';
import { OwnersReview } from './../model/owners-review';
import { ReservationDTO } from './../model/reservation-dto.model';
import { UserService } from 'src/app/service/user.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PeriodDTO } from '../model/period-dto.model';
import { ConfigService } from '../service/config.service';
import { ReservationService } from '../service/reservation.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-owner-reservations',
  templateUrl: './owner-reservations.component.html',
  styleUrls: ['./owner-reservations.component.css']
})
export class OwnerReservationsComponent implements OnInit {

  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[];
  allReservations: ReservationDTO[] = [];
  filterStatus = 0;
  clientNames: string[];
  ownerId = -1;
  name!: string;
  user:any;
  showForm = 0;
  review: OwnersReview = new OwnersReview();
  reviewForm!: FormGroup;
  success!: boolean;
  radioButtonValue: any;


  constructor(
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private reservationService: ReservationService,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private ownersReviewService: OwnersReviewService
    ) 
    {
      this.reservations = [];
      this.clientNames = [];
    }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.ownerId = params.id;
      console.log(this.ownerId);
    });

    this.reservationService.getOwnerReservations(this.ownerId).subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
      this.allReservations= this.reservations;
      for(let res of this.allReservations){
        if(res.clientId == null){
          this.clientNames.push("/");}
        this.userService.getUser(res.clientId).subscribe((result)=>{
          this.user = result;
          this.clientNames.push(this.user.firstName + ' ' + this.user.surname);});
        
      }
    });
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl(null),
      endDate: new FormControl(null)
    });
    //this.getClientNames();

    this.reviewForm = this.formBuilder.group({
      description: new FormControl(''),
      appeared: new FormControl(),
      isReported: new FormControl()
    })
  }

  filterReservationsByStatus(status: string): void{
    this.reservations = this.allReservations.filter((val) => val.reservationStatus.toString() === status);
  }

  findReservationsInDateRange(form: FormGroup): void {
    const maxDate = new Date(8640000000000000);
    const minDate = new Date(-8640000000000000);
    let timePeriod = new PeriodDTO();
    // timePeriod = this.reservationsDateRangeForm.getRawValue();
    timePeriod.startDate = form.value.startDate;
    timePeriod.endDate = form.value.endDate;
    console.log(timePeriod);
    if (timePeriod.startDate == null){
      timePeriod.startDate = minDate;
    }
    if (timePeriod.endDate == null){
      timePeriod.endDate = maxDate;
    }
    this.reservations = this.allReservations.filter((val) => new Date(val.reservedPeriod.startDate) >= timePeriod.startDate &&
      new Date(val.reservedPeriod.endDate) <= timePeriod.endDate);
    // this.reservations = this.reservationService.getReservationsInDateRange(timePeriod).subscribe(
    //     (res: ReservationDTO[]) => {
    //     this.reservations = res;
    //   }
    // );
  }

  getClientName(id: number) : string{
    let firstName: string ='';
    let surname: string = '';
    this.userService.getUser(id).subscribe((res)=>this.user = res);
    this.name = this.user.firstName + ' ' + this.user.surname;
    return  this.name;
  }

  getClientNames(){
    for(let res of this.reservations){
      this.clientNames.push(this.getClientName(res.clientId));
    }
  }

  saveReview(){
    if(this.radioButtonValue == 1){
      this.review.isReported = true;
      this.review.appeared = true;
    } else if(this.radioButtonValue == 2){
      this.review.isReported = false;
      this.review.appeared = false;
    } else{
      this.review.isReported = false;
      this.review.appeared = true;
    }
    this.review.description = this.reviewForm.value.description;
    return this.ownersReviewService.createReview(this.review).subscribe(res=>console.log(res));
  }

  openSnackBar(){
    let message: string;
    if( this.success == true){
      message = 'Response sent';
    } else{
      message = 'Response failed to send';
    }
    this.snackbar.open(message,'cancel');
    window.location.reload();
  }

}
