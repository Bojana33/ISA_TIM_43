import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  displayedColumns: string[] = ['firstName', 'surname', 'email', 'phoneNumber', 'delete'];

  constructor(private userService: UserService,
              private httpClient: HttpClient,
              private router: Router) { }

  ngOnInit(): void {
     //this.users = this.userService.getAll();
    // this.httpClient.get<any>('http://localhost:8090/api/user/all').subscribe(results => {
    //   console.log(results);
    //   this.users = results;
    // });
    this.getAllUsers();
  }

  getAllUsers(){
    return this.userService.getAll().subscribe(res=>{console.log(res); this.users = res});
  }

  deleteUser(id:number){
    this.userService.delete(id);
    window.location.reload();
  }

}
