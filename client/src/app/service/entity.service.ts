import {ApiService} from "./api.service";
import {ConfigService} from "./config.service";
import {HttpClient} from "@angular/common/http";
import {CottageDTO} from "../model/cottage-dto.model";
import {FreeEntityDTO} from "../model/free-entity-dto";
import {AdditionalServicesDTO} from "../model/AdditionalServicesModel/additional-services-dto.model";
import {Injectable} from "@angular/core";
import {ReservationDTO} from "../model/reservation-dto.model";
@Injectable({
  providedIn: 'root'
})
export class EntityService {
  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
  }

  reserve(request: ReservationDTO){
    return this.apiService.post(this.config.entity_url + '/reserve', JSON.parse(JSON.stringify(request)));
  }
}
