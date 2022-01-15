import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CottageService} from '../service/cottage.service';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ConfigService} from '../service/config.service';
import {UserService} from '../service/user.service';
import {CottageDTO} from '../model/cottage-dto.model';
import {EntityService} from '../service/entity.service';


@Component({
  selector: 'app-register-cottage',
  templateUrl: './register-cottage.component.html',
  styleUrls: ['./register-cottage.component.css']
})
export class RegisterCottageComponent implements OnInit {
  cottageCreationForm!: FormGroup;
  private selectedFile!: File;
  private _rooms!: FormArray;
  constructor(
    private cottageService: CottageService,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private config: ConfigService,
    private userService: UserService,
    private entityService: EntityService
  ) {
  }
  cottage: CottageDTO = new CottageDTO();
  ngOnInit(): void {
    this.initializeForm();
  }

  onSubmit(): void {
    this.cottage = this.cottageCreationForm.getRawValue();
    this.cottage.reservations = [];
    this.cottage.cottageOwnerId = this.userService.currentUser.id;
    this.http.post<any>(this.config.cottages_url, this.cottage)
      .subscribe(res => {
        console.log(res.id);
        this.uploadImage(res.id);
      });
    this.approvedSnackBar();
  }
  // tslint:disable-next-line:typedef
  approvedSnackBar(){
    this.snackbar.open('Cottage is created', 'cancel');
  }
  public onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];
  }
  private uploadImage(id: number): void{
    const data: FormData = new FormData();
    data.append('imageUrl', this.selectedFile, this.selectedFile.name);
    this.entityService.saveImage(data, id).subscribe(res => {console.log(res); });
  }
  createRooms(): FormGroup {
    return this.formBuilder.group({
      numberOfBeds: 0,
    });
  }
  get getRooms(): FormArray {
    return this.cottageCreationForm.get('rooms') as FormArray;
  }
  addRoom(): void{
    this._rooms = this.cottageCreationForm.get('rooms') as FormArray;
    this._rooms.push(this.createRooms());
  }
  deleteRoom(index: number): void {
    this._rooms = this.cottageCreationForm.get('rooms') as FormArray;
    this._rooms.removeAt(index);
  }
  private initializeForm(): void {
    this.cottageCreationForm = this.formBuilder.group({
      cottageName: new FormControl('', [Validators.required, Validators.minLength(5)]),
      description: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(511)]),
      pricePerDay: new FormControl('', [Validators.required, Validators.min(0)]),
      maxNumberOfGuests: new FormControl('', [Validators.required, Validators.min(0), Validators.max(300)]),
      address: new FormGroup({
        country: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required]),
        houseNumber: new FormControl('', [Validators.required])
      }),
      rooms: this.formBuilder.array([this.createRooms()])
    });
  }
}
