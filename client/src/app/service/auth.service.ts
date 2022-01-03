import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { Observable } from 'rxjs';
import { baseUrl } from 'src/environments/environment';
import { ApiService } from './api.service';
import { UserService } from './user.service';
import { ConfigService } from './config.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiService: ApiService,
    private userService: UserService,
    private config: ConfigService,
    private router: Router
  ) {
  }

  private access_token = null;

  login(user: { email: any; password: any; }) {
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    // const body = `username=${user.username}&password=${user.password}`;
    const body = {
      'email' : user.email,
      'password' : user.password
    };
    return this.apiService.post(this.config.login_url, JSON.stringify(body), loginHeaders)
      .pipe(map((res) => {
        console.log(res.body.accessToken);
        localStorage.setItem("access_token", res.body.accessToken);
        localStorage.setItem("id_token", res.body.idToken);
        this.access_token = res['body'].accessToken;
      }));
  }

  signup(user: any) {
    const signupHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    return this.apiService.post(this.config.signup_url, JSON.stringify(user), signupHeaders)
      .pipe(map(() => {
        console.log('Sign up success');
      }));
  }

  logout() {
      localStorage.removeItem("access_token");
      localStorage.removeItem("id_token");
      this.userService.currentUser = null;
      this.access_token = null;
      this.router.navigate(['/login']);
  }

  changePassowrd(passwordChanger: any) {
    const passwordChangerHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    return this.apiService.post(this.config.change_password_url, JSON.stringify(passwordChanger), passwordChangerHeaders)
    .pipe(map(() => {
      console.log('Password changer success');
    }));
  }

  tokenIsPresent() {
    if (this.access_token != undefined && this.access_token != null)
      return true;
    return localStorage.getItem("access_token") != undefined && localStorage.getItem("access_token") != null;
  }

  getToken() {
    if (this.access_token != undefined  && this.access_token != null)
    {
      return this.access_token;
    }
    else {
      console.log(localStorage.getItem("access_token"));
      return this.access_token;
    }
  }

}
