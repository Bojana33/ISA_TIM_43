import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDeleteRequest } from '../model/user-delete-request';
import { UserDeleteRequestService } from '../service/user-delete-request.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-delete-profile',
  templateUrl: './delete-profile.component.html',
  styleUrls: ['./delete-profile.component.css']
})
export class DeleteProfileComponent implements OnInit {

  requestObj!: UserDeleteRequest;

  request = new FormGroup({
    description: new FormControl('')
  })

  constructor(
    private userService: UserService,
    private requestService: UserDeleteRequestService,
    private dialogRef: MatDialogRef<DeleteProfileComponent>,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  addRequest(form:any){
    this.requestObj = new UserDeleteRequest();
    this.requestObj.description = form.value.description;
    this.requestObj.userId = this.userService.currentUser.id;
  }

  createUserDeleteRequest(){
    this.addRequest(this.request);
    this.requestService.saveRequest(JSON.parse(JSON.stringify(this.requestObj))).subscribe(res=>{console.log(res)})
    this.onClose();
    this.openSnackBar();
  }

  onClose() {
    this.dialogRef.close();
  }

  openSnackBar(){
    this.snackBar.open('Your delete request was sent successfully!', 'cancel');
  }

}
