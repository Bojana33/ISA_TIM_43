import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { ComplaintService } from './../service/complaint.service';
import { Component, OnInit } from '@angular/core';
import { UserComplaint } from '../model/user-complaint';

@Component({
  selector: 'app-complaints',
  templateUrl: './complaints.component.html',
  styleUrls: ['./complaints.component.css']
})
export class ComplaintsComponent implements OnInit {

  complaints: UserComplaint[] = [];
  complaint: UserComplaint = new UserComplaint();
  sendResponseForm!: FormGroup;
  displayedColumns: string[] = ['id', 'description', 'response'];
  showForm = 0;
  success!: boolean;

  constructor(
    private userComplaintService: ComplaintService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getAllComplaints();

    this.sendResponseForm = this.formBuilder.group({
      response: new FormControl(this.complaint.response)
    })
  }

  getAllComplaints(){
    return this.userComplaintService.getAllComplaints().subscribe(res=>{console.log(res); this.complaints = res});
  }

  sendResponse(body:any){
    body.response = this.sendResponseForm.value.response;
    return this.userComplaintService.sendResponse(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar()}, error => {this.success=false; this.openSnackBar();});
  }

  openSnackBar(){
    let message: string;
    if( this.success == true){
      message = 'Response sent';
    } else{
      message = 'Response failed to send';
    }
    this.snackbar.open(message,'cancel');
    window.location.reload();
  }

}
