import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { PeriodDTO } from '../model/period-dto.model';
import { ReservationDTO } from '../model/reservation-dto.model';
import { ReservationService } from '../service/reservation.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-admin-reservations',
  templateUrl: './admin-reservations.component.html',
  styleUrls: ['./admin-reservations.component.css']
})
export class AdminReservationsComponent implements OnInit {

  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[];
  allReservations: ReservationDTO[] = [];
  filterStatus = 0;
  clientNames: string[];
  ownerId = -1;
  name!: string;
  user:any;
  showForm = 0;
  reviewForm!: FormGroup;
  success!: boolean;
  radioButtonValue: any;
  pricesList: any;
  allEarnings: any;
  completedReservations: any;


  constructor(
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private reservationService: ReservationService,
    private userService: UserService,
    private snackbar: MatSnackBar
    ) 
    {
      this.reservations = [];
      this.clientNames = [];
    }

  ngOnInit(): void {

    this.reservationService.getAllReservations().subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
      this.allReservations= this.reservations;
      for(let res of this.allReservations){
        if(res.clientId == null){
          res.clientName = "/";}
        this.userService.getUser(res.clientId).subscribe((result)=>{
          this.user = result;
          res.clientName =this.user.firstName + ' ' + this.user.surname});
      this.allEarnings = this.allReservations.filter((val) => val.reservationStatus.toString() === 'COMPLETED').map(a => (a.price - a.ownersIncome));
      this.completedReservations = this.allEarnings.length;
      this.allEarnings = this.allEarnings.reduce((a: number, b: number) => a + b, 0);
        
      }
    });
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl(null),
      endDate: new FormControl(null)
    });

    this.reviewForm = this.formBuilder.group({
      description: new FormControl(''),
      appeared: new FormControl(),
      isReported: new FormControl()
    })
  }

  private updatePrices(): void {
    this.pricesList = this.reservations.filter((val) => val.reservationStatus.toString() === 'COMPLETED').map(a => (a.price - a.ownersIncome));
    this.pricesList = this.pricesList.reduce((a: number, b: number) => a + b, 0);
  }

  filterReservationsByStatus(status: string): void{
    this.reservations = this.allReservations.filter((val) => val.reservationStatus.toString() === status);
    this.updatePrices();
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
      this.updatePrices();
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


}
