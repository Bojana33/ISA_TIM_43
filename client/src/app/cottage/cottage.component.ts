import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {CottageService} from '../service/cottage.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CalendarComponent} from '../calendar/calendar.component';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {ReservationDTO} from '../model/reservation-dto.model';
import {ReservationService} from '../service/reservation.service';
import {DatePipe} from '@angular/common';


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {
  cottage: CottageDTO = new CottageDTO();
  showForm = 1;
  cottageUpdateForm = this.formBuilder.group({
    cottageName: new FormControl(this.cottage.cottageName, Validators.required),
    description: new FormControl(this.cottage.description, Validators.required),
    pricePerDay: new FormControl(this.cottage.pricePerDay, [Validators.min(0), Validators.required]),
    maxNumberOfGuests: new FormControl(this.cottage.maxNumberOfGuests, [Validators.required, Validators.min(0)])
  });
  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private reservationService: ReservationService,
    private formBuilder: FormBuilder,
    private router: Router,
    private datePipe: DatePipe

  ) {
  }

  ngOnInit(): void {
    this.httpClient.get<any>(this.config.cottages_url + '/' + this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.cottage = response;
      });
    // if (this.hasSignedIn() && this.loggedUserIsOwner()){
    //   // @ts-ignore
    //   this.cottageUpdateForm.get('cottageName')?.setValue(this.cottage.cottageName);
    // }
  }
  // tslint:disable-next-line:typedef
  hasRole(role: string){
    return this.userService.loggedRole(role);
  }

  // tslint:disable-next-line:typedef
  hasSignedIn() {
    return !!this.userService.currentUser;
  }
  // NE MENJAJ OVO U ===. NECE RADITI!!!!!!!
  // tslint:disable-next-line:typedef
  loggedUserIsOwner(){
    return this.userService.currentUser.id == this.cottage.cottageOwnerId;
  }
  // tslint:disable-next-line:typedef
  deleteCottage(){
    // return this.httpClient.delete(this.config.cottages_url + '/' + this.cottage.id);
    // this.snackbar.open('cottage delete request sent', 'cancel');
    return this.cottageService.deleteCottage(this.cottage.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }
  // tslint:disable-next-line:typedef
  updateCottage(){
    // this.snackbar.open('cottage delete request sent', 'cancel');
    this.cottageService.updateCottage(this.cottage).subscribe(
    res => {
      this.cottage = res;
      // this.router.navigate(['/cottages']);
    });
  }

  createNewReservation($event: ReservationDTO){
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform($event.reservedPeriod.startDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedReservationEndDate = datepipe.transform($event.reservedPeriod.endDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedSaleStartDate = datepipe.transform($event.salePeriod.startDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedSaleEndDate = datepipe.transform($event.salePeriod.endDate, 'yyyy-MM-dd HH:mm:ss');
    console.log($event.reservedPeriod.startDate);
    console.log(formatedReservationStartDate);
    // @ts-ignore
    $event.reservedPeriod.startDate = new Date(formatedReservationStartDate.toString());
    console.log($event.reservedPeriod.startDate);
    // @ts-ignore
    $event.reservedPeriod.endDate = new Date(formatedReservationEndDate.toString());
    // @ts-ignore
    $event.salePeriod.startDate = new Date(formatedSaleStartDate.toString());
    // @ts-ignore
    $event.salePeriod.endDate = new Date(formatedSaleEndDate.toString());
    this.reservationService.createNewReservationForEntity($event).subscribe(
        (res: any) => {
        console.log(res);
      });
  }
}
