import { Injectable } from '@angular/core';
import {ApiService} from '../api.service';
import {ConfigService} from '../config.service';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../../model/cottage-dto.model';
import {BoatDTO} from '../../model/boat-dto';

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
  getAll() {
    return this.apiService.get(this.config.boat_url);
  }
  deleteBoat(id: number){
    return this.apiService.delete(this.config.boat_url + '/' + id);
  }
  updateBoat(boat: BoatDTO){
    return this.apiService.put(this.config.boat_url + '/' + boat.id, boat);
  }
  getAllForOwner(id: number){
    return this.apiService.get(this.config.boat_url + '/getForOwner/' + id);
  }
}
