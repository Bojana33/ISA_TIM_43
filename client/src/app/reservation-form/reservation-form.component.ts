import {Component, Input, Output, EventEmitter} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
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
  @Output() createdReservationEvent = new EventEmitter<ReservationDTO>();
  cottageReservationForm = this.formBuilder.group({
    price: ['', [Validators.required]],
    numberOfGuests: ['', [Validators.required, Validators.min(1), Validators.max(300)]],
    additionalNotes: ['', [Validators.required]],
    reservedPeriod: new FormGroup({
      startDate: new FormControl(['', [Validators.required]]),
      endDate: new FormControl(['', [Validators.required]])
    }),
    salePeriod: new FormGroup({
      startDate: new FormControl(['', [Validators.required]]),
      endDate: new FormControl(['', [Validators.required]])
    })
    // additionalServices: new FormGroup({
    //   name: new FormControl([''], [Validators.required]),
    //   price: new FormControl([''], [Validators.min(0)])
    // })
      // reservationPeriodStartDate: ['', [Validators.required]],
      // reservationPeriodEndDate: ['', [Validators.required]],
      // salePeriodStartDate: ['', [Validators.required]],
      // salePeriodEndDate: ['', [Validators.required]]
  });
  constructor(
    private formBuilder: FormBuilder,
    private cottageService: CottageService,
    private configService: ConfigService,
    private apiService: ApiService
  ) {}

  private reservation: ReservationDTO | undefined;

  makeReservation(): void {
    this.reservation = this.cottageReservationForm.getRawValue();
    console.log(this.reservation);
    // @ts-ignore
    this.reservation.entityId = this.entityId;
    // console.log(this.reservation, 'pre emita');
    this.createdReservationEvent.emit(this.reservation);
  }

}
