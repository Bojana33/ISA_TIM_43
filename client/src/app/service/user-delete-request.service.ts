import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserDeleteRequestService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  saveRequest(data:any){
    return this.apiService.post(this.config.user_delete_request_url + '/add_delete_request',data);
  }

  getRequest(id:any){
    return this.apiService.get(this.config.user_delete_request_url + '/get_request/' + id);
  }

  approveRequest(request:any){
    return this.apiService.post(this.config.user_delete_request_url + '/approve_request',request);
  }

  rejectRequest(request:any){
    return this.apiService.post(this.config.user_delete_request_url + '/reject_request',request);
  }


}
