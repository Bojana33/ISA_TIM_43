import { Router } from '@angular/router';
import { AuthService } from './../service/auth.service';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(
    private userService : UserService,
    private authService : AuthService,
    private router : Router
  ) { }

  ngOnInit(): void {
  }

  logout(){
    this.authService.logout();
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }

}
