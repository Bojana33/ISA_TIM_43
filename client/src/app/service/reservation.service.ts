import { Injectable } from '@angular/core';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {ReservationDTO} from '../model/reservation-dto.model';
import {PeriodDto} from '../model/period-dto.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(
    private api: ApiService,
    private config: ConfigService
  ) { }

  getReservationsForEntity(entityId: number): any{
    return this.api.get(this.config.entity_url + '/reservations/' + entityId);
  }
  createNewReservationForEntity(reservation: ReservationDTO): any{
    return this.api.post(this.config.entity_url + '/reservations', reservation);
  }


  getOwnerReservations(ownerId:number) {
    return this.api.get(this.config.reservation_url + '/get_owner_reservations/' + ownerId);
  }
  getReservationsInDateRange(periodDTO: PeriodDto): any{
    return this.api.get(this.config.entity_url + '/reservations/' + periodDTO.id, periodDTO);
  }
}
