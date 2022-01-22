import { UserService } from './../service/user.service';
import { ReviewStatus } from './../enum/review-status.enum';
import { OwnersReviewService } from './../service/owners-review.service';
import { OwnersReview } from './../model/owners-review';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-owners-reviews',
  templateUrl: './owners-reviews.component.html',
  styleUrls: ['./owners-reviews.component.css']
})
export class OwnersReviewsComponent implements OnInit {

  reviews: OwnersReview[] = [];
  review: OwnersReview = new OwnersReview();
  sendResponseForm!: FormGroup;
  displayedColumns: string[] = ['id', 'description', 'client', 'approve', 'deny'];
  success!: boolean;
  clientNames: string[];
  user:any;

  constructor(
    private ownersReviewService: OwnersReviewService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar,
    private userService: UserService
  ) { this.clientNames = [];}

  ngOnInit(): void {
     this.getAllReviews();
     
  }

  getAllReviews(){
    return this.ownersReviewService.getAllReviews().subscribe(res=>{console.log(res); this.reviews = res;
      for(let res of this.reviews){
        if(res.reservationDTO.clientId == null){
          res.clientName = "";}
        this.userService.getUser(res.reservationDTO.clientId).subscribe((result)=>{
          this.user = result;
          res.clientName = this.user.firstName + ' ' + this.user.surname})};
    });
     
  }

  approveReview(body:any){
    body.reviewStatus = ReviewStatus.INAPPROPRIATE;
    return this.ownersReviewService.sendResponse(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar();}, error => {this.success=false; this.openSnackBar();});
  }
  denyReview(body:any){
    body.reviewStatus = ReviewStatus.APPROPRIATE;
    return this.ownersReviewService.sendResponse(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar();}, error => {this.success=false; this.openSnackBar();});
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
