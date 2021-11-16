import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {CustomValidationService} from '../../service/custom-validation.service';
import { UserService } from '../../service/user.service';
import {User} from '../../model/user';

@Component({
  selector: 'app-signup-client',
  templateUrl: './signup-client.component.html',
  styleUrls: ['./signup-client.component.css']
})
export class SignupClientComponent implements OnInit {
  user: User;
  form!: FormGroup;
  isSuccess: any;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private customValidator: CustomValidationService,
    private userService: UserService
  ) {
      this.user = new User();
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: '',
      surname: '',
      email: '',
      password: ['', Validators.required],
      repeatPassword: ['', Validators.required],
      address: '',
      city: '',
      country: '',
      phoneNumber: ''
    },
      {validator: this.customValidator.passwordMatchValidator('password', 'repeatPassword')});
  }

  submit(): void {
    console.log();
    this.isSuccess = true;
    this.http.post('http://localhost:8090/auth/signupClient', this.form.getRawValue())
      .subscribe(res => {
        console.log(res);
      });
  }
}
