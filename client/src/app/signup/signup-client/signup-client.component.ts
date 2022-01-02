import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {CustomValidationService} from '../../service/custom-validation.service';
import { UserService } from '../../service/user.service';
import {User} from '../../model/user';
import {RegistrationRequest} from '../../model/registration-request';
import { UserRequest } from 'src/app/model/user-request';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-signup-client',
  templateUrl: './signup-client.component.html',
  styleUrls: ['./signup-client.component.css']
})
export class SignupClientComponent implements OnInit {
  user: User;
  form!: FormGroup;
  isSuccess = false;
  request!: UserRequest;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private customValidator: CustomValidationService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {
      this.user = new User();
  }

  ngOnInit(): void {
    this.isSuccess = false;
    this.form = this.formBuilder.group({
      firstName: '',
      surname: '',
      email: '',
      password: ['', Validators.required, Validators.minLength(3), Validators.maxLength(64)],
      repeatPassword: ['', Validators.required, Validators.minLength(3), Validators.maxLength(64)],
      address: '',
      city: '',
      country: '',
      phoneNumber: ''
    },
      {validator: this.customValidator.passwordMatchValidator('password', 'repeatPassword')});
  }

  submit(): void {
    this.saveRequest(this.form);
    console.log();
    this.http.post('http://localhost:8090/auth/signupClient', this.request)
      .subscribe(res => {
        console.log(res);
        this.isSuccess = true;
        this.snackBar.open('Your registration is successful! We\'ve sent you a verification mail to your email account.', 'cancel');
      });
    if (!this.isSuccess)
      this.snackBar.open('Something went wrong. Make sure that this email is not already in use.', 'cancel');
  }

  saveRequest(form:any) {
    this.request = new UserRequest();
    this.request.firstName = form.value.firstName;
    this.request.surname = form.value.surname;
    this.request.addressDTO.city = form.value.city;
    this.request.addressDTO.country = form.value.country;
    this.request.addressDTO.houseNumber = form.value.houseNumber;
    this.request.addressDTO.street = form.value.street;
    this.request.email = form.value.email;
    this.request.password = form.value.password;
    this.request.phoneNumber = form.value.phoneNumber;
  }
}
