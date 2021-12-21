import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CottageService} from '../service/cottage.service';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ConfigService} from '../service/config.service';

@Component({
  selector: 'app-register-cottage',
  templateUrl: './register-cottage.component.html',
  styleUrls: ['./register-cottage.component.css']
})
export class RegisterCottageComponent implements OnInit {
  cottageCreationForm = this.formBuilder.group({
    cottageName: ['', [Validators.required]],
    description: ['', [Validators.required]],
    pricePerDay: ['', [Validators.required, Validators.min(0)]],
    maxNumberOfGuests: ['', [Validators.required, Validators.min(0)]]
  });
  // public id!: number;
  // public cottageName: string;
  // public description: string;
  // public photos: string[];
  // public maxNumberOfGuests: number;
  // public pricePerDay: number;
  // public addressDTO: AddressDTO;
  // public roomsDTO: RoomDTO[];
  // public reservationsDTO: ReservationDTO[];

  constructor(
    private cottageService: CottageService,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private config: ConfigService
  ) {
  }

  ngOnInit(): void {

  }

  onSubmit(): void {
    this.http.post(this.config.cottages_url, this.cottageCreationForm.getRawValue())
      .subscribe(res => {
        console.log(res);
      });
    this.approvedSnackBar();
  }
  // tslint:disable-next-line:typedef
  approvedSnackBar(){
    this.snackbar.open('Cottage is created', 'cancel');
  }
}
