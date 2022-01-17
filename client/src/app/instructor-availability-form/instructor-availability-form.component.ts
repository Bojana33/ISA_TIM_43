import { ConfigService } from './../service/config.service';
import { ApiService } from './../service/api.service';
import { UserService } from './../service/user.service';
import { InstructorAvailabilityDto } from './../model/instructor-availability-dto';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AvailabilityType } from '../enum/availability-type.enum';

@Component({
  selector: 'app-instructor-availability-form',
  templateUrl: './instructor-availability-form.component.html',
  styleUrls: ['./instructor-availability-form.component.css']
})
export class InstructorAvailabilityFormComponent implements OnInit {

  @Input() showForm: any;
  form!: FormGroup;
  instructorAvailability!: InstructorAvailabilityDto;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private apiService: ApiService,
    private config: ConfigService
    ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      periodDTO: new FormGroup({
        startDate: new FormControl(['', [Validators.required]]),
        endDate: new FormControl(['', [Validators.required]])
      })
    });
  }

  saveInstructorAvailability(){
    this.instructorAvailability = this.form.getRawValue();
    this.instructorAvailability.ownerId = this.userService.currentUser.id;
    if(this.showForm == 2){
      this.instructorAvailability.availabilityType = AvailabilityType.AVAILABLE;
    }
    if(this.showForm == 3){
      this.instructorAvailability.availabilityType = AvailabilityType.UNAVAILABLE;
    }
    return this.apiService.post(this.config.owner_url + '/save_instructor_availability',JSON.parse(JSON.stringify(this.instructorAvailability))).subscribe(res =>console.log(res));
  }

}
