import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CottageService} from '../service/cottage.service';
import {ConfigService} from '../service/config.service';
import {ApiService} from '../service/api.service';
import {ReservationDTO} from '../model/reservation-dto.model';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent{
  @Input() entityId: any;
  cottageReservationForm = this.formBuilder.group({
    price: ['', [Validators.required]],
    numberOfGuests: ['', [Validators.required, Validators.min(1), Validators.max(300)]],
    additionalNotes: ['', [Validators.required]],
    reservationPeriodStartDate: ['', [Validators.required]],
    reservationPeriodEndDate: ['', [Validators.required]],
    salePeriodStartDate: ['', [Validators.required]],
    salePeriodEndDate: ['', [Validators.required]]
  });
  constructor(
    private formBuilder: FormBuilder,
    private cottageService: CottageService,
    private configService: ConfigService,
    private apiService: ApiService
  ) { }

  private reservation: ReservationDTO | undefined;

  onSubmit(): void {
    this.reservation = this.cottageReservationForm.getRawValue();
    // @ts-ignore
    this.reservation.entityId = this.entityId;
    console.log(this.reservation);
  }

}
