import { ApiService } from './api.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ConfigService } from './config.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegistrationRequest } from '../model/registration-request';

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private api: ApiService
  ) { }

  form: FormGroup = new FormGroup({
    rejectionReason: new FormControl('')
  });

  getRequest(id:number) {
    return this.httpClient.get(this.config.registration_request_url + '/get_request/' + id);
  }

  rejectRequest(id:number, request:RegistrationRequest){
    return this.httpClient.put(this.config.registration_request_url + '/reject_request/' + id, request);
  }

  saveRequest(data:any){
    return this.httpClient.post(this.config.signup_url,JSON.parse(JSON.stringify(data)));
  }

}


