import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-owner-review-form',
  templateUrl: './owner-review-form.component.html',
  styleUrls: ['./owner-review-form.component.css']
})
export class OwnerReviewFormComponent implements OnInit {
  @Input() reservationId: any;
  ownerReviewForm!: FormGroup;
  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
  }

  initializeForm(): void{
    this.ownerReviewForm = this.formBuilder.group({
      description: ['', [Validators.required]],
      isReported: [false, [Validators.required]],
      reviewStatus: ['', [Validators.required]]
    });
  }
}
