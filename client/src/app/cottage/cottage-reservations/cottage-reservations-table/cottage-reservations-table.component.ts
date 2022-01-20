import {Component, EventEmitter, OnInit} from '@angular/core';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {ReservationDTO} from '../../../model/reservation-dto.model';
import {HttpClient} from '@angular/common/http';
import {ReservationService} from '../../../service/reservation.service';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PeriodDTO} from '../../../model/period-dto.model';
import {Sort} from '@angular/material/sort';

@Component({
  selector: 'app-cottage-reservations-table',
  templateUrl: './cottage-reservations-table.component.html',
  styleUrls: ['./cottage-reservations-table.component.css']
})
export class CottageReservationsTableComponent implements OnInit {

  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[] = [];
  allReservations: ReservationDTO[] = [];
  reservationInDateRange: ReservationDTO[] = [];
  pricesList: any;
  filterStatus = 0;
  cottageId = -1;
  showForm = 1;

  constructor(private config: ConfigService,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private formBuilder: FormBuilder,
              private reservationService: ReservationService) {
  }

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
      this.reservationInDateRange = this.reservations;
    });
  }

  filterReservationsByStatus(status: string): void {
    if ((this.filterStatus === 1 && status === 'FREE') || (this.filterStatus === 2 && status === 'RESERVED')
      || (this.filterStatus === 3 && status === 'COMPLETED') || (this.filterStatus === 4 && status === 'CANCELED')) {
      this.reservations = this.reservationInDateRange;
      return;
    }
    console.log(status, this.filterStatus);
    this.reservations = this.reservationInDateRange.filter((val) => val.reservationStatus.toString() === status);
    this.updatePrices();
  }
  findReservationsInDateRange(form: FormGroup): void {
    const maxDate = new Date(8640000000000000);
    const minDate = new Date(-8640000000000000);
    const timePeriod = new PeriodDTO();
    // timePeriod = this.reservationsDateRangeForm.getRawValue();
    timePeriod.startDate = form.value.startDate;
    timePeriod.endDate = form.value.endDate;
    console.log(timePeriod);
    if (timePeriod.startDate == null) {
      timePeriod.startDate = minDate;
    }
    if (timePeriod.endDate == null) {
      timePeriod.endDate = maxDate;
    }
    this.reservations = this.allReservations.filter((val) => new Date(val.reservedPeriod.startDate) >= timePeriod.startDate &&
      new Date(val.reservedPeriod.endDate) <= timePeriod.endDate);
    this.updatePrices();
  }

  private updatePrices(): void {
    this.pricesList = this.reservations.map(a => a.price);
    this.pricesList = this.pricesList.reduce((a: number, b: number) => a + b, 0);
  }

  // @ts-ignore
  sortData(sort: Sort): number {
    const data = this.reservations.slice();
    if (!sort.active || sort.direction === '') {
      this.reservations = data;
      return 0;
    }

    this.reservations = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'creationDate':
          return compare(a.creationDate.toString(), b.creationDate.toString(), isAsc);
        case 'reservationStart':
          return compare(a.reservedPeriod.startDate.toString(), b.reservedPeriod.startDate.toString(), isAsc);
        case 'reservationEnd':
          return compare(a.reservedPeriod.endDate.toString(), b.reservedPeriod.endDate.toString(), isAsc);
        case 'saleStart':
          return compare(a.reservedPeriod.startDate.toString(), b.reservedPeriod.startDate.toString(), isAsc);
        case 'saleEnd':
          return compare(a.salePeriod.startDate.toString(), b.salePeriod.startDate.toString(), isAsc);
        case 'price':
          return compare(a.price, b.price, isAsc);
        case 'numberOfGuests':
          return compare(a.numberOfGuests, b.numberOfGuests, isAsc);
        case 'reservationStatus':
          return compare(a.reservationStatus, b.reservationStatus, isAsc);
        case 'clientId':
          return compare(a.clientId, b.clientId, isAsc);
        default:
          return 0;
      }
    });
  }

  clearDateRangeFilter(): void {
    this.reservationsDateRangeForm.reset();
    this.findReservationsInDateRange(this.reservationsDateRangeForm);
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean): number {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
