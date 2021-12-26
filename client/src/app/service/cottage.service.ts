import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';

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
  deleteCottage(id: number){
    return this.apiService.delete(this.config.cottages_url + '/' + id);
  }
  updateCottage(cottage: CottageDTO){
    return this.apiService.put(this.config.cottages_url + '/' + cottage.id, cottage);
  }
}