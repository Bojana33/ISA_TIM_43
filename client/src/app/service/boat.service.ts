import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {FreeEntityDTO} from "../model/free-entity-dto";
@Injectable({
  providedIn: 'root'
})
export class BoatService {
  private boatsUrl: string;
  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
    this.boatsUrl = 'http://localhost:8090/boats';
  }


  getFreeBoats(request: FreeEntityDTO) {
    return this.apiService.post('http://localhost:8090/boats/findFree',  JSON.parse(JSON.stringify(request)));
  }
  //getSorted(cottages: CottageDTO[], criterion: string, asc: boolean){
    //return this.apiService.post('http://localhost:8090/cottages/sorted' + '/' + criterion + '/' + asc, JSON.parse(JSON.stringify(cottages)));
  //}
}
