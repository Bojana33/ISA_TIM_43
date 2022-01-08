import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ReservationDTO} from "../model/reservation-dto.model";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CottageService} from "../service/cottage.service";
import {FreeEntityDTO} from "../model/free-entity-dto";
import {CottagesUserComponent} from "../user-quest/cottages-user/cottages-user.component";

@Component({
  selector: 'app-client-reservations',
  templateUrl: './client-reservations.component.html',
  styleUrls: ['./client-reservations.component.css']
})
export class ClientReservationsComponent implements OnInit {

  form!: FormGroup;
  request!: FreeEntityDTO;
  constructor(private formBuilder: FormBuilder,
              private cottageService: CottageService,
              private cottages: CottagesUserComponent) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      entityType: '',
      numberOfGuests: '',
        reservationStartDate: '',
      reservationEndDate: ''
    });
  }

  submit(): void {
    this.saveRequest(this.form);
    console.log(this.request);
    this.cottageService.getFreeCottages(this.request).subscribe(
      res => {
        this.cottages.showCottages(res);
      }
    );
  }

  saveRequest(form:any) {
    this.request = new FreeEntityDTO();
    this.request.startDate = form.value.reservationStartDate.toISOString();
    this.request.endDate = form.value.reservationEndDate.toISOString();
    this.request.type = 'cottage';
  }
}
