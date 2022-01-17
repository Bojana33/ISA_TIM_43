import {Component, EventEmitter, OnInit} from '@angular/core';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {ReservationDTO} from '../../../model/reservation-dto.model';
import {HttpClient} from '@angular/common/http';
import {ReservationService} from '../../../service/reservation.service';
import {Observable} from 'rxjs';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PeriodDTO} from '../../../model/period-dto.model';
import {colors} from '../../../utils/colors';

@Component({
  selector: 'app-cottage-reservations-table',
  templateUrl: './cottage-reservations-table.component.html',
  styleUrls: ['./cottage-reservations-table.component.css']
})
export class CottageReservationsTableComponent implements OnInit {

  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[] = [];
  allReservations: ReservationDTO[] = [];
  filterStatus = 0;
  cottageId = -1;
  constructor(private config: ConfigService,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private formBuilder: FormBuilder,
              private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.cottageId = params.id;
      console.log(this.cottageId);
    });
    this.getReservations();
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl(null),
      endDate: new FormControl(null)
    });
  }

  private getReservations(): void {
    this.reservationService.getReservationsForEntity(this.cottageId).subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
      this.allReservations = this.reservations;
    });
  }

  filterReservationsByStatus(status: string): void{
    this.reservations = this.allReservations.filter((val) => val.reservationStatus.toString() === status);
  }
  findReservationsInDateRange(form: FormGroup): void {
    const maxDate = new Date(8640000000000000);
    const minDate = new Date(-8640000000000000);
    let timePeriod = new PeriodDTO();
    // timePeriod = this.reservationsDateRangeForm.getRawValue();
    timePeriod.startDate = form.value.startDate;
    timePeriod.endDate = form.value.endDate;
    console.log(timePeriod);
    if (timePeriod.startDate == null){
      timePeriod.startDate = minDate;
    }
    if (timePeriod.endDate == null){
      timePeriod.endDate = maxDate;
    }
    this.reservations = this.allReservations.filter((val) => new Date(val.reservedPeriod.startDate) >= timePeriod.startDate &&
      new Date(val.reservedPeriod.endDate) <= timePeriod.endDate);
    // this.reservations = this.reservationService.getReservationsInDateRange(timePeriod).subscribe(
    //     (res: ReservationDTO[]) => {
    //     this.reservations = res;
    //   }
    // );
  }
}
