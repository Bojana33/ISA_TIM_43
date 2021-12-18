import { RegistrationRequestDTO } from './../registration-requests/registration-requests.component';
import { FormControl, FormGroup } from '@angular/forms';
import { ConfigService } from './config.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) { }

  form: FormGroup = new FormGroup({
    rejectionReason: new FormControl('')
  });

  getRequest(id:number) {
    return this.httpClient.get(this.config.registration_request_url + '/get_request/' + id);
  }

  rejectRequest(id:number, request:RegistrationRequestDTO){
    return this.httpClient.put(this.config.registration_request_url + '/reject_request/' + id, request);
  }

}


