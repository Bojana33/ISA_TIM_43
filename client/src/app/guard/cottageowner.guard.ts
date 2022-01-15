import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from '../service/user.service';
import {UserDTO} from '../model/user-dto';

@Injectable({
  providedIn: 'root'
})
export class CottageownerGuard implements CanActivate {
  private currentUser!: Promise<void | null>;
  constructor(private router: Router, private userService: UserService) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.currentUser = this.userService.initUser();
    if (this.currentUser) {
      if (this.userService.loggedRole('COTTAGEOWNER')) {
        return true;
      } else {
        this.router.navigate(['/403']);
        return false;
      }

    } else {
      console.log('NOT AN COTTAGEOWNER ROLE');
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
  }
  }


}
