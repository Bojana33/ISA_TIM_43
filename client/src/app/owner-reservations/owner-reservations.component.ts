import { ReservationDTO } from './../model/reservation-dto.model';
import { UserService } from 'src/app/service/user.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PeriodDto } from '../model/period-dto.model';
import { ConfigService } from '../service/config.service';
import { ReservationService } from '../service/reservation.service';

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
  constructor(private config: ConfigService,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private formBuilder: FormBuilder,
              private reservationService: ReservationService,
              private userService: UserService) {
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
    });
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl(null),
      endDate: new FormControl(null)
    });
    this.getClientNames();
  }

  filterReservationsByStatus(status: string): void{
    this.reservations = this.allReservations.filter((val) => val.reservationStatus.toString() === status);
  }

  findReservationsInDateRange(form: FormGroup): void {
    const maxDate = new Date(8640000000000000);
    const minDate = new Date(-8640000000000000);
    let timePeriod = new PeriodDto();
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
    this.userService.getUser(id).subscribe(res=>{console.log(res);firstName= res.firstName; surname = res.surname});
    this.name = firstName + ' ' + surname;
    return  this.name;
  }

  getClientNames(){
    for(let res of this.reservations){
      this.clientNames.push(this.getClientName(res.clientId));
    }
  }

}
