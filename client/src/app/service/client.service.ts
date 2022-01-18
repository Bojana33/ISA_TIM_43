import {Injectable} from "@angular/core";
import {ApiService} from "./api.service";
import {ConfigService} from "./config.service";
import {HttpClient} from "@angular/common/http";
import {FreeEntityDTO} from "../model/free-entity-dto";

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private clientsUrl: string;
  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
    this.clientsUrl = 'http://localhost:8090/client';
  }


  getAllSubscriptions() {
    return this.apiService.get(this.clientsUrl + '/getAllSubscriptions');
  }
}
