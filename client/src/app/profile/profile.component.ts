import { DeleteProfileComponent } from './../delete-profile/delete-profile.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';
import {Address} from '../model/address';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userProfile: any;
  showForm = 1;
  choosed!: string;
  choices: string[] = ['Reservations', 'Availability'];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private dialog: MatDialog) {
  }

  ngOnInit(): void {
    //this.userService.getUser(this.router.snapshot.params.id).subscribe((response)=>
     // this.userProfile = response);
    this.userService.getLoggedUserInfo().subscribe(res => this.userProfile = res);
  }

  openDialog(){
    const dialogConfig = new MatDialogConfig();
    this.dialog.open(DeleteProfileComponent, dialogConfig);
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }

  onItemChange(event:any){
    this.choosed = event.target.value;
  }

  isLoggedIdEqualToProfileId(id:number){
    return this.userService.currentUser.id == id;
  }
}
