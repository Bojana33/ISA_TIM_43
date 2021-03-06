import {ApiService} from "./api.service";
import {ConfigService} from "./config.service";
import {HttpClient} from "@angular/common/http";
import {CottageDTO} from "../model/cottage-dto.model";
import {FreeEntityDTO} from "../model/free-entity-dto";
import {AdditionalServicesDTO} from "../model/AdditionalServicesModel/additional-services-dto.model";
import {Injectable} from "@angular/core";
import {ReservationDTO} from "../model/reservation-dto.model";
import {RentalTimeDto} from '../model/rental-time-dto';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(    private apiService: ApiService,
                  private config: ConfigService,
                  private http: HttpClient) { }

  saveEntityPhoto(data: FormData, cottageId: number) {
    return this.http.post(this.config.entity_url + '/save_entity_image/' + cottageId, data);
  }
  savePhoto(data: FormData, cottageId: number) {
    return this.http.post(this.config.entity_url + '/save_image/' + cottageId, data);
  }

  reserve(request: ReservationDTO){
    return this.apiService.post(this.config.entity_url + '/reserve', JSON.parse(JSON.stringify(request)));
  }
  fastReservation(request: ReservationDTO){
    return this.apiService.post(this.config.entity_url + '/fastReservation', JSON.parse(JSON.stringify(request)));
  }
  getEntity(entityId: number){
    return this.apiService.get(this.config.entity_url + '/getEntityById/' + entityId);
  }
  subscribeToEntity(entityId: number){
    return this.apiService.post(this.config.entity_url + '/subscribe/' + entityId, null);
  }
  unsubscribe(entityId: number){
    return this.apiService.put(this.config.entity_url + '/unsubscribe/' + entityId, null);
  }
  isSubscribed(entityId: number){
    return this.apiService.get(this.config.entity_url + '/isSubscribed/' + entityId);
  }
  getRentalTimeForEntity(entityId: number){
    return this.apiService.get(this.config.entity_url + /rentalTime/ + entityId);
  }
  createRentalTime(rentalTime: RentalTimeDto){
    return this.apiService.post(this.config.entity_url + '/rentalTime', rentalTime);
  }
}
