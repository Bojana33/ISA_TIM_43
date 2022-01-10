import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ReservationDTO} from "../model/reservation-dto.model";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CottageService} from "../service/cottage.service";
import {FreeEntityDTO} from "../model/free-entity-dto";
import {CottagesUserComponent} from "../user-quest/cottages-user/cottages-user.component";
import {Cottage} from "../model/cottage";
import {MatSnackBar} from "@angular/material/snack-bar";
import { CottageDTO } from '../model/cottage-dto.model';

@Component({
  selector: 'app-client-reservations',
  templateUrl: './client-reservations.component.html',
  styleUrls: ['./client-reservations.component.css']
})
export class ClientReservationsComponent implements OnInit {


  form!: FormGroup;
  request!: FreeEntityDTO;
  entity: CottageDTO[] = [];
  show!: boolean;
  //sortBy!: 0;
  //public value!: string;
  public sortMethod: string[] = [];
  public value: string = '';
  constructor(private formBuilder: FormBuilder,
              private cottageService: CottageService,
              private snackBar: MatSnackBar) { this.show = false;}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      entityType: '',
      numberOfGuests: '',
        reservationStartDate: '',
      reservationEndDate: '',
      grade: '',
      country: '',
      city: ''
    });
  }

  submit(): void {
    this.saveRequest(this.form);
    this.openDialog();
    this.cottageService.getFreeCottages(this.request).subscribe(
      res => {
        console.log(res.body);
        this.entity = res.body;
        if (this.entity.length === 0)
          this.snackBar.open('There are no free cottages with this specification. Try with another period.', 'cancel');
      }
    );
  }

  saveRequest(form:any) {
    console.log(form);
    this.request = new FreeEntityDTO();
    this.request.startDate = form.value.reservationStartDate.toISOString();
    this.request.endDate = form.value.reservationEndDate.toISOString();
    this.request.type = 'cottage';
    this.request.numberOfGuests = form.value.numberOfGuests;
    this.request.grade = form.value.grade;
    this.request.country = form.value.country;
    this.request.city = form.value.city;
    this.show = true;
  }

  onItemChange(item:any){
    this.value = item;
  }

  openDialog(){
    this.sortMethod = ['Price ascending', 'Price descending', 'Average grade ascending', 'Average grade descending'];
  }
}
