import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {UserDTO} from '../model/user-dto';
import {User} from '../model/user';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ReservationDTO} from '../model/reservation-dto.model';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {ReservationService} from '../service/reservation.service';
import {PeriodDTO} from '../model/period-dto.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  user!: any;
  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[] = [];
  allReservations: ReservationDTO[] = [];
  allEarnings: any;
  completedReservations: any;
  pricesList: any;
  activeEntities: any;
  filterStatus = 0;
  showForm = 1;
  canceledReservationsNumber = 0;
  activeButton = 'DASHBOARD';
  constructor(
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient,
    private formBuilder: FormBuilder,
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe((response) => {
      this.user = response;
      this.getAllReservations();
      this.reservationsDateRangeForm = this.formBuilder.group({
        startDate: new FormControl(null),
        endDate: new FormControl(null)
      });
    });
  }
  private getAllReservations(): void {
    this.reservationService.getOwnerReservations(this.user.id).subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
      this.allReservations = this.reservations;
      this.canceledReservationsNumber = this.allReservations.filter((val) => val.reservationStatus.toString() === 'CANCELED').length;
      this.allEarnings = this.allReservations.filter((val) => val.reservationStatus.toString() === 'COMPLETED').map(a => a.price);
      this.completedReservations = this.allEarnings.length;
      this.allEarnings = this.allEarnings.reduce((a: number, b: number) => a + b, 0);
      this.activeEntities = this.allReservations.map(a => a.entityId);
      this.activeEntities = new Set(this.activeEntities);
      this.updatePrices();
    });
  }
  private updatePrices(): void {
    this.pricesList = this.reservations.filter((val) => val.reservationStatus.toString() === 'COMPLETED').map(a => a.price);
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
  hasSignedIn() {
    return !!this.userService.currentUser;
  }
  getAll(){
  }

  hasRole(role: string){
    return this.userService.loggedRole(role);
  }
  isReservationActive(reservation: ReservationDTO): boolean{
    const dateNow = new Date();
    return (new Date(reservation.reservedPeriod.startDate) <=  dateNow && new Date(reservation.reservedPeriod.endDate) >= dateNow);
  }
  activeReservations(): void {
    const dateNow = new Date();
    console.log(dateNow);
    this.reservations = this.allReservations.filter((val) => new Date(val.reservedPeriod.startDate) <=  dateNow &&
      new Date(val.reservedPeriod.endDate) >= dateNow);
    this.updatePrices();
  }
  createNewReservation($event: ReservationDTO){
    this.reservationService.createNewReservationsForClient($event).subscribe(
      (res: any) => {
        console.log(res);
      });
  }
}
