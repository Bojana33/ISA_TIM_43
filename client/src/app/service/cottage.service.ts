import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {FreeEntityDTO} from "../model/free-entity-dto";
@Injectable({
  providedIn: 'root'
})
export class CottageService {
  private cottagesUrl: string;
  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
    this.cottagesUrl = 'http://localhost:8090/cottages';
  }


  getAll() {
    return this.apiService.get(this.config.cottages_url);
  }
  getAllForOwner(id: number){
    return this.apiService.get(this.config.cottages_url + '/getForOwner/' + id);
  }
  deleteCottage(id: number){
    return this.apiService.delete(this.config.cottages_url + '/' + id);
  }
  updateCottage(cottage: CottageDTO){
    return this.apiService.put(this.config.cottages_url + '/' + cottage.id, cottage);
  }
  saveImage(data: FormData, cottageId: number){
    return this.http.post(this.config.cottage_url + '/save_image/' + cottageId, data);
  }
  getFreeCottages(request: FreeEntityDTO) {
    return this.apiService.post('http://localhost:8090/cottages/findFree',  JSON.parse(JSON.stringify(request)));
  }
  getSorted(cottages: CottageDTO[], criterion: string, asc: boolean){
    return this.apiService.post('http://localhost:8090/cottages/sorted' + '/' + criterion + '/' + asc, JSON.parse(JSON.stringify(cottages)));
  }
}
