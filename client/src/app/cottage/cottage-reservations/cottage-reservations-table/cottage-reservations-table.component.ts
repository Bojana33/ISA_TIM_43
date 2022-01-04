import { Component, OnInit } from '@angular/core';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {ReservationDTO} from '../../../model/reservation-dto.model';
import {HttpClient} from '@angular/common/http';
import {ReservationService} from '../../../service/reservation.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-cottage-reservations-table',
  templateUrl: './cottage-reservations-table.component.html',
  styleUrls: ['./cottage-reservations-table.component.css']
})
export class CottageReservationsTableComponent implements OnInit {

  reservations: ReservationDTO[];
  cottageId = -1;
  constructor(private config: ConfigService,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private reservationService: ReservationService) {
    this.reservations = [];
  }

  ngOnInit(): void {
    // this.reservationService.getReservationsForEntity(activatedRoute)
    this.activatedRoute.params.subscribe(params => {
      this.cottageId = params.id;
      console.log(this.cottageId);
    });
    this.reservationService.getReservationsForEntity(this.cottageId).subscribe((params: ReservationDTO[]) => {
      this.reservations = params;
    });
  }
}
