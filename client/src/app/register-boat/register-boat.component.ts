import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CottageService} from '../service/cottage.service';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ConfigService} from '../service/config.service';
import {UserService} from '../service/user.service';
import {EntityService} from '../service/entity.service';
import {CottageDTO} from '../model/cottage-dto.model';
import {BoatDTO} from '../model/boat-dto';
import {BoatService} from '../service/boatService/boat.service';

@Component({
  selector: 'app-register-boat',
  templateUrl: './register-boat.component.html',
  styleUrls: ['./register-boat.component.css']
})
export class RegisterBoatComponent implements OnInit {
  boatCreationForm!: FormGroup;
  private selectedFile!: File;
  boat: BoatDTO = new BoatDTO();
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private config: ConfigService,
    private userService: UserService,
    private entityService: EntityService
  ) { }

  onSubmit(): void {
    this.boat = this.boatCreationForm.getRawValue();
    this.boat.reservations = [];
    this.boat.boatOwnerId = this.userService.currentUser.id;
    this.http.post<any>(this.config.boat_url, this.boat)
      .subscribe(res => {
        this.uploadImage(res.id);
      });
    this.approvedSnackBar();
  }
  approvedSnackBar(): void{
    this.snackbar.open('Cottage is created', 'cancel')._dismissAfter(10);
  }
  ngOnInit(): void {
    this.initializeForm();
  }
  public onFileChanged(event: any): void {
    this.selectedFile = event.target.files[0];
  }
  private uploadImage(id: number): void{
    const data: FormData = new FormData();
    data.append('imageUrl', this.selectedFile, this.selectedFile.name);
    this.entityService.saveImage(data, id).subscribe(res => {console.log(res); });
  }
  private initializeForm(): void {
    this.boatCreationForm = this.formBuilder.group({
      boatName: new FormControl('', [Validators.required, Validators.minLength(5)]),
      description: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(511)]),
      pricePerDay: new FormControl('', [Validators.required, Validators.min(0)]),
      maxNumberOfGuests: new FormControl('', [Validators.required, Validators.min(0), Validators.max(300)]),
      address: new FormGroup({
        country: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required]),
        houseNumber: new FormControl('', [Validators.required])
      }),
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
      boatType: new FormControl(this.boat.type
      ),
      enginePower: new FormControl(this.boat.enginePower, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(31)]
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
      cancellationFee: new FormControl(this.boat.cancellationFee, [
        Validators.required,
        Validators.min(0),
        Validators.max(100)]
      ),
      fishingEquipment: new FormControl(this.boat.fishingEquipment, [
        Validators.required,
        Validators.min(0),
        Validators.max(300)]
      ),
      houseRules: new FormControl(this.boat.houseRules, [
        Validators.required,
        Validators.min(0),
        Validators.max(300)]
      )
    });
  }
}
