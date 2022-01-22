import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {CottageService} from '../service/cottage.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReservationDTO} from '../model/reservation-dto.model';
import {ReservationService} from '../service/reservation.service';
import {EntityService} from '../service/entity.service';


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit{
  cottage: CottageDTO = new CottageDTO();
  addressFormated: any;
  showRentalTimes = false;
  private selectedFile!: File;
  clicked = false;
  showForm = 1;
  // @ts-ignore
  cottageUpdateForm: FormGroup;
  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private reservationService: ReservationService,
    private formBuilder: FormBuilder,
    private entityService: EntityService
  ) {
  }

  ngOnInit(): void {
    this.loadData();
    // if (this.hasSignedIn() && this.loggedUserIsOwner()){
    //   // @ts-ignore
    //   this.cottageUpdateForm.get('cottageName')?.setValue(this.cottage.cottageName);
    // }
  }

  private loadData(): void {
    this.httpClient.get<any>(this.config.cottages_url + '/' + this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.cottage = response;
        this.cottageUpdateForm = this.formBuilder.group({
          cottageName: new FormControl(this.cottage.cottageName, [
            Validators.required,
            Validators.minLength(5)]
          ),
          description: new FormControl(this.cottage.description, [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(511)]
          ),
          pricePerDay: new FormControl(this.cottage.pricePerDay, [
            Validators.min(0),
            Validators.required]
          ),
          maxNumberOfGuests: new FormControl(this.cottage.maxNumberOfGuests, [
            Validators.required,
            Validators.min(0),
            Validators.max(300)]
          ),
          address: new FormGroup({
            country: new FormControl(this.cottage.address.country, [Validators.required]),
            city: new FormControl(this.cottage.address.city, [Validators.required]),
            street: new FormControl(this.cottage.address.street, [Validators.required]),
            houseNumber: new FormControl(this.cottage.address.houseNumber, [Validators.required])
          }),
        });
        this.addressFormated = this.cottage.address.city + ', ' + this.cottage.address.street
          + ', ' + this.cottage.address.houseNumber;
      });
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
    // this.snackbar.open('cottage delete request sent', 'cancel');
    return this.cottageService.deleteCottage(this.cottage.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }
  // tslint:disable-next-line:typedef
  updateCottage(form: FormGroup){
    console.log(form.value.address);
    this.cottage.cottageName = form.value.cottageName;
    this.cottage.description = form.value.description;
    this.cottage.maxNumberOfGuests = form.value.maxNumberOfGuests;
    this.cottage.pricePerDay = form.value.pricePerDay;
    this.cottage.address.country = form.value.address.country;
    this.cottage.address.street = form.value.address.street;
    this.cottage.address.city = form.value.address.city;
    this.cottage.address.houseNumber = form.value.address.houseNumber;
    console.log(this.cottage.address);
    return this.cottageService.updateCottage(this.cottage).subscribe(
      res => {
        console.log(res.id);
        this.uploadImage(res.id);
        if (res.status === 200){
          this.snackbar.open('Cottage update successful', 'cancel');
        }else{
          this.snackbar.open(`There's a problem with update`, 'cancel');
        }
        this.loadData();
      });
  }

  // tslint:disable-next-line:typedef
  createNewReservation($event: ReservationDTO){
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
}
