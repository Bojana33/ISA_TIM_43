import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CottageService} from '../service/cottage.service';
import {UserType} from '../enum/user-type';
import {AddressDTO} from '../model/address-dto.model';
import {RoomDTO} from '../model/room-dto.model';
import {ReservationDTO} from '../model/reservation-dto.model';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {

  cottageCreationForm = this.formBuilder.group({
    cottageName: ['', [Validators.required]],
    description: ['', [Validators.required]],
    pricePerDay: ['', [Validators.required]],
    maxNumberOfGuests: ['', [Validators.required]]
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
    private http: HttpClient
  ) {
  }

  ngOnInit(): void {

  }

  onSubmit(): void {
    console.log();
    this.http.post('http://localhost:8090/cottages', this.cottageCreationForm.getRawValue())
      .subscribe(res => {
        console.log(res);
      });
  }
}
