import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {OwnersReview} from '../model/owners-review';
import {ReservationDTO} from '../model/reservation-dto.model';
import {OwnerReviewMinimalDto} from '../model/owner-review-minimal-dto';

@Component({
  selector: 'app-owner-review-form',
  templateUrl: './owner-review-form.component.html',
  styleUrls: ['./owner-review-form.component.css']
})
export class OwnerReviewFormComponent implements OnInit {
  @Input() reservationId: any;
  @Input() clientId: any;
  radioButtonValue = 0;
  ownerReview!: OwnerReviewMinimalDto;
  ownerReviewForm!: FormGroup;
  constructor(
    private formBuilder: FormBuilder
  ) {const rnd = Math.random(); console.log(rnd)}

  ngOnInit(): void {
    this.ownerReview = new OwnerReviewMinimalDto();
    console.log(this.clientId, this.reservationId);
    this.ownerReviewForm = this.formBuilder.group({
      reservationId: new FormControl(this.reservationId),
      clientId: new FormControl(this.clientId),
      description: new FormControl('', [Validators.required])
    });
  }

  initializeForm(): void{
  }
  submitForm(){
    if (this.radioButtonValue == 1){
      this.ownerReview.isReported = true;
      this.ownerReview.appeared = true;
    } else if (this.radioButtonValue == 2){
      this.ownerReview.isReported = false;
      this.ownerReview.appeared = false;
    } else{
      this.ownerReview.isReported = false;
      this.ownerReview.appeared = true;
    }
    this.ownerReview = this.ownerReviewForm.getRawValue();
    console.log(this.ownerReview);
  }
}
