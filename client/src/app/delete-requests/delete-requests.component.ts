import { User } from './../model/user';
import { UserService } from './../service/user.service';
import { ConfigService } from './../service/config.service';
import { ApiService } from './../service/api.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-delete-requests',
  templateUrl: './delete-requests.component.html',
  styleUrls: ['./delete-requests.component.css']
})
export class DeleteRequestsComponent implements   OnInit {

  requests:any;
  users: User[] = [];

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests(){
    this.apiService.get(this.config.user_delete_request_url + '/on_hold_requests').subscribe(
      response => {
        console.log(response);
        this.requests = response;
        this.requests.forEach((element: any) => {
          this.userService.getUser(element.userId).subscribe(res => {console.log(res);this.users.push(res)}); 
        });
      })
  }

}
