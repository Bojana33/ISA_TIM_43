import {Component, EventEmitter, OnInit} from '@angular/core';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {ReservationDTO} from '../../../model/reservation-dto.model';
import {HttpClient} from '@angular/common/http';
import {ReservationService} from '../../../service/reservation.service';
import {Observable} from 'rxjs';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PeriodDto} from '../../../model/period-dto.model';
import {colors} from '../../../utils/colors';

@Component({
  selector: 'app-cottage-reservations-table',
  templateUrl: './cottage-reservations-table.component.html',
  styleUrls: ['./cottage-reservations-table.component.css']
})
export class CottageReservationsTableComponent implements OnInit {

  reservationsDateRangeForm!: FormGroup;
  reservations: ReservationDTO[];
  cottageId = -1;
  constructor(private config: ConfigService,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private formBuilder: FormBuilder,
              private reservationService: ReservationService) {
    this.reservations = [];
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.cottageId = params.id;
      console.log(this.cottageId);
    });
    this.reservationService.getReservationsForEntity(this.cottageId).subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
    });
    this.reservationsDateRangeForm = this.formBuilder.group({
      startDate: new FormControl([null, [Validators.required]]),
      endDate: new FormControl([null, [Validators.required]])
    });
  }

  findReservationsInDateRange(): void {
    let timePeriod = new PeriodDto();
    timePeriod = this.reservationsDateRangeForm.getRawValue();
    this.reservations = this.reservations.filter((val) => new Date(val.reservedPeriod.startDate) >= timePeriod.startDate &&
      new Date(val.reservedPeriod.endDate) <= timePeriod.endDate);
    // this.reservations = this.reservationService.getReservationsInDateRange(timePeriod).subscribe(
    //     (res: ReservationDTO[]) => {
    //     this.reservations = res;
    //   }
    // );
  }
}
