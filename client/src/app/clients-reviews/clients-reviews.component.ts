import { ClientsReviewStatus } from './../enum/clients-review-status.enum';
import { ClientsReviewService } from './../service/clients-review.service';
import { ClientsReview } from './../model/clients-review';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-clients-reviews',
  templateUrl: './clients-reviews.component.html',
  styleUrls: ['./clients-reviews.component.css']
})
export class ClientsReviewsComponent implements OnInit {

  reviews: ClientsReview[] = [];
  review: ClientsReview = new ClientsReview();
  sendResponseForm!: FormGroup;
  displayedColumns: string[] = ['id', 'description', 'grade', 'approve', 'deny'];
  success!: boolean;
  clientNames: string[];
  user:any;

  constructor(
    private clientsReviewService: ClientsReviewService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar,
    private userService: UserService
  ) { this.clientNames = [];}

  ngOnInit(): void {
     this.getAllReviews();
  }

  getAllReviews(){
    return this.clientsReviewService.getAllReviews().subscribe(res=>{console.log(res); this.reviews = res;
      for(let res of this.reviews){
        if(res.reservationDTO.clientId == null){
          this.clientNames.push("/");}
        this.userService.getUser(res.reservationDTO.clientId).subscribe((result)=>{
          this.user = result;
          this.clientNames.push(this.user.firstName + ' ' + this.user.surname)})};
    });
  }

  approvePublication(body:any){
    body.status = ClientsReviewStatus.PUBLIC;
    return this.clientsReviewService.sendResponse(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar();}, error => {this.success=false; this.openSnackBar();});
  }
  denyPublication(body:any){
    body.status = ClientsReviewStatus.NONPUBLIC;
    return this.clientsReviewService.sendResponse(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar();}, error => {this.success=false; this.openSnackBar();});
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
