import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-instructor-availability-form',
  templateUrl: './instructor-availability-form.component.html',
  styleUrls: ['./instructor-availability-form.component.css']
})
export class InstructorAvailabilityFormComponent implements OnInit {

  @Input() showForm: any;
  form!: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      reservedPeriod: new FormGroup({
        startDate: new FormControl(['', [Validators.required]]),
        endDate: new FormControl(['', [Validators.required]])
      })
    });
  }

}
