import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {CottageService} from '../service/cottage.service';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {
  cottage: CottageDTO = ({} as any);
  user: User = ({} as any);
  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService
  ) {
  }

  ngOnInit(): void {
    this.user = this.userService.currentUser
    this.httpClient.get<any>(this.config.cottages_url + '/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.cottage = response;
      });
  }
  hasRole(role: string){
    return this.userService.loggedRole(role);
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }
  loggedUserIsOwner(): boolean{
    return this.userService.currentUser.id == this.cottage.cottageOwnerId;
  }
  deleteCottage(){
    this.snackbar.open('cottage delete request sent');
    // return this.httpClient.delete(this.config.cottages_url + '/' + this.cottage.id);
    return this.cottageService.deleteCottage(this.cottage.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }
  updateCottage(){

  }
}
