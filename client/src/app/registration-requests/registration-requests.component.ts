import { OwnerType } from './../enum/owner-type';
import { ConfigService } from './../service/config.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegistrationRequest } from '../model/registration-request';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  requests: RegistrationRequest[]=[];
  request: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests(){
    this.httpClient.get<any>(this.config.registration_request_url + '/not_confirmed_requests').subscribe(
      response => {
        console.log(response);
        this.requests = response;
      }

    )
  }

  approveRequest(id:number) {
    this.approvedSnackBar();
    return this.httpClient.get(this.config.registration_request_url + '/approve_request/' + id).subscribe(res =>{
      console.log(res)});
  }

  approvedSnackBar(){
    this.snackbar.open('Request is approved','cancel');
  }

}
