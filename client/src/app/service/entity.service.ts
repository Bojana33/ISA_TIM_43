import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(    private apiService: ApiService,
                  private config: ConfigService,
                  private http: HttpClient) { }

  saveImage(data: FormData, cottageId: number){
    return this.http.post(this.config.entity_url + '/save_image/' + cottageId, data);
  }
}
