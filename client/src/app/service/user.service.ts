import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user';
import {UserDTO} from "../model/user-dto";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser:any;
  private usersUrl: string;

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
    this.usersUrl = 'http://localhost:8090/auth/signup';
  }

  initUser() {
    console.log('dosao u init user');
    const promise = this.apiService.get(this.config.refresh_token_url).toPromise()
      .then(res => {
        if (res.access_token !== null) {
          return this.getMyInfo().toPromise()
            .then(user => {
              this.currentUser = user;
            });
        }else if(localStorage.getItem("access_token") !== null) {
          return this.getMyInfo().toPromise()
            .then(user => {
              this.currentUser = user;
            });
        }
        else {
          return null;
        }
      })
      .catch(() => null);
    return promise;
  }

  setupUser(user: any) {
    this.currentUser = user;
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url)
      .pipe(map(user => {
        this.currentUser = user;
        return user;
      }));
  }

  getAll() {
    return this.apiService.get(this.config.users_url);
  }

  getUser(id:any) {
    console.log('dosao u get user');
    if(this.currentUser) {
      return this.apiService.get(this.config.api_url + "/get_user/" + id);
    }
    var storageUser = window.localStorage.getItem('user');
    if (storageUser) {
      try {
        this.currentUser =  JSON.parse(storageUser);
      } catch (e) {
        window.localStorage.removeItem('user');
      }
    }
    return this.apiService.get(this.config.api_url + "/get_user/" + id);
  }

  save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  loggedRole(role:string){
    if (this.currentUser == null){
      return false;
    }
    if (JSON.stringify(this.currentUser.authorities).search('ROLE_'+role) !== -1){
      return true;
    }
    return false;
  }

  update(data:UserDTO){
    console.log(data);
    return this.apiService.post('http://localhost:8090/user/update', data).subscribe((res)=>{console.log});
  }

  saveAdmin(data:any){
    return this.apiService.post(this.config.auth_url + '/signupAdmin',JSON.parse(JSON.stringify(data)));
  }
}
