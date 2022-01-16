import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  getList(){
    return this.apiService.get(this.config.user_complaint_url + '/get_list_of_reservations').subscribe(res=>console.log(res));
  }

  getAllComplaints(){
    return this.apiService.get(this.config.user_complaint_url + '/get_complaints');
  }

  sendResponse(body:any){
    return this.apiService.post(this.config.user_complaint_url + '/send_response',body);
  }
}
