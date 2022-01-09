import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {BoatService} from '../service/boatService/boat.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {ReservationDTO} from '../model/reservation-dto.model';
import {ReservationService} from '../service/reservation.service';
import {BoatDTO} from '../model/boat-dto';

@Component({
  selector: 'app-boat',
  templateUrl: './boat.component.html',
  styleUrls: ['./boat.component.css']
})
export class BoatComponent implements OnInit {
  boat: BoatDTO = new BoatDTO();
  addressFormated: any;
  showForm = 1;
  boatUpdateForm = this.formBuilder.group({
    boatName: new FormControl(this.boat.name, [
      Validators.required,
      Validators.minLength(5)]
    ),
    description: new FormControl(this.boat.description, [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(511)]
    ),
    pricePerDay: new FormControl(this.boat.pricePerDay, [
      Validators.min(0),
      Validators.required]
    ),
    maxNumberOfGuests: new FormControl(this.boat.maxNumberOfGuests, [
      Validators.required,
      Validators.min(0)]
    )
  });

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private boatService: BoatService,
    private reservationService: ReservationService,
    private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit(): void {
    this.httpClient.get<any>(this.config.boat_url + '/' + this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.boat = response;
        this.addressFormated = this.boat.address.city + ', ' + this.boat.address.street
          + ', ' + this.boat.address.houseNumber;
      });
    // if (this.hasSignedIn() && this.loggedUserIsOwner()){
    //   // @ts-ignore
    //   this.boatUpdateForm.get('boatName')?.setValue(this.boat.boatName);
    // }
  }

  // tslint:disable-next-line:typedef
  hasRole(role: string) {
    return this.userService.loggedRole(role);
  }

  // tslint:disable-next-line:typedef
  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  // NE MENJAJ OVO U ===. NECE RADITI!!!!!!!
  // tslint:disable-next-line:typedef
  loggedUserIsOwner() {
    return this.userService.currentUser.id == this.boat.boatOwnerId;
  }

  // tslint:disable-next-line:typedef
  deleteboat() {
    // this.snackbar.open('boat delete request sent', 'cancel');
    return this.boatService.deleteBoat(this.boat.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }

  // tslint:disable-next-line:typedef
  updateboat() {
    // this.snackbar.open('boat delete request sent', 'cancel');
    return this.boatService.updateBoat(this.boat).subscribe(
      res => {
        this.boat = res;
        this.ngOnInit();
      });
  }

  // tslint:disable-next-line:typedef
  createNewReservation($event: ReservationDTO) {
    this.reservationService.createNewReservationForEntity($event).subscribe(
      (res: any) => {
        console.log(res);
      });
  }
}
