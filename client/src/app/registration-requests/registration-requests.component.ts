import { OwnerType } from './../enum/owner-type';
import { ConfigService } from './../service/config.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegistrationRequestService } from '../service/registration-request.service';

export class RegistrationRequestDTO {
  constructor(
    public id: number,
    public firstName: string,
    public surname: string,
    public email: string,
    public phoneNumber: string,
    public registrationExplanation: string,
    public rejectionReason: string,
    public userType: UserType

  ){
  }
}
import { RegistrationRequest } from '../model/registration-request';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  requests: RegistrationRequestDTO[] = [];
  request: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getRequests();
  }

  // tslint:disable-next-line:typedef
  getRequests(){
    this.httpClient.get<any>(this.config.registration_request_url + '/not_confirmed_requests').subscribe(
      response => {
        console.log(response);
        this.requests = response;
      }

    );
  }

  // tslint:disable-next-line:typedef
  approveRequest(id: number) {
    this.approvedSnackBar();
    return this.httpClient.get(this.config.registration_request_url + '/approve_request/' + id).subscribe(res => {
      console.log(res); });
  }

  // tslint:disable-next-line:typedef
  approvedSnackBar(){
    this.snackbar.open('Request is approved', 'cancel');
  }

}
