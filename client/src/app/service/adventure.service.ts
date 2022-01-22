import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import {FreeEntityDTO} from "../model/free-entity-dto";
import {CottageDTO} from "../model/cottage-dto.model";
import {AdventureDTO} from "../model/adventure-dto";

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
    return this.apiService.delete(this.config.adventure_url + '/delete_adventure/'+id);
  }
  getFreeAdventures(request: FreeEntityDTO) {
    return this.apiService.post(this.config.adventure_url + '/findFreeAdventures',  JSON.parse(JSON.stringify(request)));
  }
  getSorted(adventures: AdventureDTO[], criterion: string, asc: boolean){
    return this.apiService.post('http://localhost:8090/adventures/sorted' + '/' + criterion + '/' + asc, JSON.parse(JSON.stringify(adventures)));
  }
}
