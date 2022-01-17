import { ClientsReviewStatus } from './../enum/clients-review-status.enum';
import { ClientsReviewService } from './../service/clients-review.service';
import { ClientsReview } from './../model/clients-review';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

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

  constructor(
    private clientsReviewService: ClientsReviewService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
     this.getAllReviews();
  }

  getAllReviews(){
    return this.clientsReviewService.getAllReviews().subscribe(res=>{console.log(res); this.reviews = res});
  }

  approvePublication(body:any){
    body.status = ClientsReviewStatus.PUBLIC;
    return this.clientsReviewService.update(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar()}, error => {this.success=false; this.openSnackBar();});
  }
  denyPublication(body:any){
    body.status = ClientsReviewStatus.NONPUBLIC;
    return this.clientsReviewService.update(body).subscribe(res=>{console.log(res); this.success=true; this.openSnackBar()}, error => {this.success=false; this.openSnackBar();});
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
