import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import { UserService } from '../service/user.service';

@Injectable()
export class GuestGuard implements CanActivate {

  currentUser: any;

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(): boolean {
    this.currentUser = this.userService.currentUser;
    if( this.currentUser != null && this.currentUser.isAdmin && this.currentUser.firstLogin)
    {
      this.router.navigate(['/change-password']);
      return false;
    }
    return true;
    //this.currentUser = this.userService.initUser();
    //if (this.currentUser) {
      //this.router.navigate(['/']);
      //return false;
    //} else {
      //return true;
    //}
  }
}
