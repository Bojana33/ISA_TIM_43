import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';
import { UserComplaint } from '../model/user-complaint';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  //getList(clientId: any){
    //return this.apiService.get(this.config.user_complaint_url + '/get_list_of_reservations', clientId);
  //}

  getAllComplaints(){
    return this.apiService.get(this.config.user_complaint_url + '/get_complaints');
  }

  sendResponse(body:any){
    return this.apiService.post(this.config.user_complaint_url + '/send_response',body);
  }
  createComplaint(body:UserComplaint){
    return this.apiService.post('http://localhost:8090/user_complaint/save_complaint', body);
  }
}
