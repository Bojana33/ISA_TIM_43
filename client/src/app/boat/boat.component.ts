import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {BoatService} from '../service/boatService/boat.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
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
  boatTypeButtonActive = false;
  showForm = 1;
  // @ts-ignore
  boatUpdateForm: FormGroup;
  //     public fishingEquipment: string = '',
  //     public houseRules: string = '',
  //     public cancellationFee: number = 0,
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
    this.loadData();
    // if (this.hasSignedIn() && this.loggedUserIsOwner()){
    //   // @ts-ignore
    //   this.boatUpdateForm.get('boatName')?.setValue(this.boat.boatName);
    // }
  }

  private loadData(): void {
    this.httpClient.get<any>(this.config.boat_url + '/' + this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.boat = response;
        this.initializeForm(this.boat);
        this.addressFormated = this.boat.address.city + ', ' + this.boat.address.street
          + ', ' + this.boat.address.houseNumber;
      });
  }

  initializeForm(boat: BoatDTO): void{
    this.boatUpdateForm = this.formBuilder.group({
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
      ),
      enginePower: new FormControl(this.boat.enginePower, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(31)]
      ),
      maxSpeed: new FormControl(this.boat.maxSpeed, [
        Validators.required,
        Validators.min(0),
        Validators.max(355)]
      ),
      length: new FormControl(this.boat.length, [
        Validators.required,
        Validators.min(0),
        Validators.max(1000)]
      ),
      capacity: new FormControl(this.boat.capacity, [
        Validators.required,
        Validators.min(0),
        Validators.max(300)]
      ),
      boatType: new FormControl(this.boat.type, [
        Validators.required]
      ),
    });
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
    return this.boatService.deleteBoat(this.boat.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }

  // tslint:disable-next-line:typedef
  updateboat(form: FormGroup) {
    // let boatForUpdate = new BoatDTO();
    // boatForUpdate = this.boat;
    this.boat.name = form.value.boatName;
    this.boat.description = form.value.description;
    this.boat.pricePerDay = form.value.pricePerDay;
    this.boat.maxNumberOfGuests = form.value.maxNumberOfGuests;
    this.boat.length = form.value.length;
    this.boat.capacity = form.value.capacity;
    this.boat.enginePower = form.value.enginePower;
    this.boat.type = form.value.boatType;
    return this.boatService.updateBoat(this.boat).subscribe(
      res => {
        if (res.status === 200){
          this.snackbar.open('Boat update successful', 'cancel');
        }else{
          this.snackbar.open(`There's a problem with update`, 'cancel');
        }
        this.loadData();
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
