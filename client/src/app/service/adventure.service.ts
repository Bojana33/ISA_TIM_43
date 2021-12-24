import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdventureService {

  adventure: any;

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private httpClient: HttpClient
  ) { }

  getAdventure(id:number){
    return this.apiService.get(this.config.adventure_url + '/get_adventure/' + id);
  }

  updateAdventure(id:number,data:any){
    return this.apiService.post(this.config.adventure_url + '/update_adventure/' + id, data);
  }

  saveAdventure(data:any){
    return this.apiService.post(this.config.adventure_url + '/add_adventure', data);
  }

  saveImage(data:any, id:number){
    return this.httpClient.post(this.config.adventure_url + '/save_adventure_image/'+id, data);
  }

  deleteAdventure(id:number){
    return this.httpClient.get(this.config.adventure_url + '/delete_adventure/'+id);
  }


}
