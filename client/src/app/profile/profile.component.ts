import { DeleteProfileComponent } from './../delete-profile/delete-profile.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: any;

  constructor(
    private userService: UserService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser(){
    this.user = this.userService.currentUser;
  }

  openDialog(){
    const dialogConfig = new MatDialogConfig();
    this.dialog.open(DeleteProfileComponent, dialogConfig);
  }

}
