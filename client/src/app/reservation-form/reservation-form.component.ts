import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReservationDTO} from '../model/reservation-dto.model';


@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit{
  @Input() entityId: any;
  @Input() clientId: any;
  @Output() createdReservationEvent = new EventEmitter<ReservationDTO>();
  cottageReservationForm!: FormGroup;
  private _additionalServices!: FormArray;

  constructor(private formBuilder: FormBuilder) {console.log(Math.random());}

  private reservation: ReservationDTO | undefined;

  ngOnInit(): void {
    console.log(this.clientId, this.entityId);
    this.cottageReservationForm = this.formBuilder.group({
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
      }),
      additionalServices: this.formBuilder.array([this.createAdditionalServices()])
    });
    // @ts-ignore
  }

  createAdditionalServices(): FormGroup{
    return this.formBuilder.group({
      name: '',
      price: ''
    });
  }
  addService(): void{
    this._additionalServices = this.cottageReservationForm.get('additionalServices') as FormArray;
    this._additionalServices.push(this.createAdditionalServices());
  }
  deleteService(index: number): void {
    this._additionalServices = this.cottageReservationForm.get('additionalServices') as FormArray;
    this._additionalServices.removeAt(index);
  }
  makeReservation(): void {
    this.reservation = this.cottageReservationForm.getRawValue();
    // @ts-ignore
    this.reservation.entityId = this.entityId;
    // @ts-ignore
    this.reservation.clientId = this.clientId;
    this.createdReservationEvent.emit(this.reservation);
  }
  get additionalServices(): FormArray {
    return this.cottageReservationForm.get('additionalServices') as FormArray;
  }
}
