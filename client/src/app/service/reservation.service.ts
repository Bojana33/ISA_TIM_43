import { Injectable } from '@angular/core';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {ReservationDTO} from '../model/reservation-dto.model';
import {PeriodDTO} from '../model/period-dto.model';
import {BoatDTO} from "../model/boat-dto";

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
  getReservationsInDateRange(periodDTO: PeriodDTO): any{
    return this.api.get(this.config.entity_url + '/reservations/' + periodDTO.id, periodDTO);
  }
  getFutureReservationsOnSale() {
    return this.api.get(this.config.reservation_url + '/getAllFutureReservationsOnSale');
  }
  getAllUserReservations(clientId:any){
    return this.api.get(this.config.reservation_url + '/getAllUserReservations/' + clientId);
  }
  cancelReservation(reservationId: any) {
    return this.api.put(this.config.reservation_url + '/cancelReservation/' + reservationId, null);
  }
  getSorted(boats: ReservationDTO[], criterion: string){
    return this.api.post('http://localhost:8090/reservations/sorted' + '/' + criterion, JSON.parse(JSON.stringify(boats)));
  }
}
