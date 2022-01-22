import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { UserService } from "../service/user.service";

@Injectable()
export class LoginGuard implements CanActivate {

  currentUser: any;

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(): boolean {
    this.currentUser = this.userService.initUser();
    if (this.userService.currentUser != null) {
      return true;
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }
}
