import { UserType } from './../enum/user-type';
import { ConfigService } from './../service/config.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

export class RegistrationRequest{
  constructor(
    public firstName: string,
    public surname: string,
    public email: string,
    public confirmed: boolean,
    public registrationExplanation: string,
    public rejectionReason: string,
    public userType: UserType

  ){
  }
}

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  requests: RegistrationRequest[]=[];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private dialog: MatDialog
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

}
