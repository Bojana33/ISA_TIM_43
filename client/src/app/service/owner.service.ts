import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  constructor(
    private api:ApiService,
    private config: ConfigService
    ) { }

  getInstructorAvailability(instructorId:number){
    return this.api.get(this.config.owner_url + '/get_instructor_availabilities/' + instructorId);
  }
  getOwnerById(id:number){
    return this.api.get(this.config.owner_url + '/getOwnerById/' + id);
  }
}
