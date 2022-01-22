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
import {EntityService} from '../service/entity.service';
import {RentalTimeDto} from '../model/rental-time-dto';

@Component({
  selector: 'app-boat',
  templateUrl: './boat.component.html',
  styleUrls: ['./boat.component.css']
})
export class BoatComponent implements OnInit {
  boat: BoatDTO = new BoatDTO();
  addressFormated: any;
  boatTypeButtonActive = false;
  showRentalTimes = false;
  showForm = 1;
  clicked = false;
  showRentalTimeForm = 0;
  rentalTime: RentalTimeDto = new RentalTimeDto();
  private selectedFile!: File;
  // @ts-ignore
  boatUpdateForm: FormGroup;
  // @ts-ignore
  rentalTimeForm: FormGroup;
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
    private entityService: EntityService
  ) {
  }

  ngOnInit(): void {
    this.loadData();
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
      name: new FormControl(this.boat.name, [
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
        Validators.min(5),
        Validators.max(1000)]
      ),
      engineNumber: new FormControl(this.boat.engineNumber, [
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
      cancellationFee: new FormControl(this.boat.cancellationFee, [
        Validators.required,
        Validators.min(0),
        Validators.max(100)]
      ),
      fishingEquipment: new FormControl(this.boat.fishingEquipment, [
        Validators.minLength(0),
        Validators.maxLength(300)]
      ),
      houseRules: new FormControl(this.boat.houseRules, [
        Validators.required,
        Validators.minLength(0),
        Validators.maxLength(300)]
      ),
      address: new FormGroup({
        country: new FormControl(this.boat.address.country, [Validators.required]),
        city: new FormControl(this.boat.address.city, [Validators.required]),
        street: new FormControl(this.boat.address.street, [Validators.required]),
        houseNumber: new FormControl(this.boat.address.houseNumber, [Validators.required])
      }),
    });
    this.rentalTimeForm = this.formBuilder.group({
      startRentalDate: new FormControl(['', [Validators.required]]),
      endRentalDate: new FormControl(['', [Validators.required]])
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
    this.boat.name = form.value.name;
    this.boat.description = form.value.description;
    this.boat.pricePerDay = form.value.pricePerDay;
    this.boat.maxNumberOfGuests = form.value.maxNumberOfGuests;
    this.boat.length = form.value.length;
    this.boat.capacity = form.value.capacity;
    this.boat.enginePower = form.value.enginePower;
    this.boat.type = form.value.boatType;
    this.boat.cancellationFee = form.value.cancellationFee;
    this.boat.houseRules = form.value.houseRules;
    this.boat.fishingEquipment = form.value.fishingEquipment;
    this.boat.engineNumber = form.value.engineNumber;
    this.boat.address.country = form.value.address.country;
    this.boat.address.street = form.value.address.street;
    this.boat.address.city = form.value.address.city;
    this.boat.address.houseNumber = form.value.address.houseNumber;
    return this.boatService.updateBoat(this.boat).subscribe(
      res => {
        this.uploadImage(res.id);
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
  public onFileChanged(event: any): void {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile.name);
  }
  private uploadImage(id: number): void{
    const data: FormData = new FormData();
    data.append('imageUrl', this.selectedFile, this.selectedFile.name);
    this.entityService.savePhoto(data, id).subscribe(res => {console.log(res); });
  }
  createRentalTime(): void{
    this.rentalTime.startDate = this.rentalTimeForm.value.startRentalDate;
    this.rentalTime.endDate = this.rentalTimeForm.value.endRentalDate;
    this.rentalTime.entityId = this.boat.id;
    console.log(this.rentalTime);
    this.entityService.createRentalTime(this.rentalTime).subscribe(res =>{
      console.log(res);
    });
  }
}
