import { Injectable } from '@angular/core';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {ReservationDTO} from '../model/reservation-dto.model';

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
    console.log('ovo je iz servisa:', reservation);
    return this.api.post(this.config.entity_url + '/reservations', reservation);
  }

  getInstructorReservations(instructorId:number){
    return this.api.get(this.config.reservation_url + '/get_instructor_reservations/' + instructorId);
  }
}
