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
  clientNames: string[];
  ownerId = -1;
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
    });
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl([null, [Validators.required]]),
      endDate: new FormControl([null, [Validators.required]])
    });
    this.getClientNames();
  }

  findReservationsInDateRange(): void {
    let timePeriod = new PeriodDto();
    timePeriod = this.reservationsDateRangeForm.getRawValue();
    this.reservations = this.reservations.filter((val) => new Date(val.reservedPeriod.startDate) >= timePeriod.startDate &&
      new Date(val.reservedPeriod.endDate) <= timePeriod.endDate);
    // this.reservations = this.reservationService.getReservationsInDateRange(timePeriod).subscribe(
    //     (res: ReservationDTO[]) => {
    //     this.reservations = res;
    //   }
    // );
  }

  getClientName(id: number) : string{
    let name: string;
    let firstName: string ='';
    let surname: string = '';
    this.userService.getUser(id).subscribe(res=>{firstName= res.firstName; surname = res.surname});
    name = firstName + ' ' + surname;
    return  name;
  }

  getClientNames(){
    for(let res of this.reservations){
      this.clientNames.push(this.getClientName(res.clientId));
    }
  }

}
