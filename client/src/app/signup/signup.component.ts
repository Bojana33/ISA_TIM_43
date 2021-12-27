import { RegistrationRequestService } from './../service/registration-request.service';
import { OwnerType } from '../enum/owner-type';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomValidationService } from '../service/custom-validation.service';
import { RegistrationRequest } from '../model/registration-request';
import { from } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form!: FormGroup;
  request!: RegistrationRequest;

  constructor(
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar,
    private customValidator: CustomValidationService,
    private requestService: RegistrationRequestService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        firstName: '',
        surname: '',
        email: '',
        password: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
        repeatPassword: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
        street: '',
        city: '',
        country: '',
        houseNumber: '',
        phoneNumber: '',
        registrationExplanation: '',
        ownerType: OwnerType
      },
      {validator: this.customValidator.passwordMatchValidator('password', 'repeatPassword')});
  }

  submit() : void{
    console.log();
    this.saveOwner(this.form);
    this.requestService.saveRequest(this.request).subscribe(res =>{console.log(res);
    });
  }

  saveOwner(form:any){
    this.request = new RegistrationRequest();
    this.request.firstName = form.value.firstName;
    this.request.surname = form.value.surname;
    this.request.addressDTO.city = form.value.city;
    this.request.addressDTO.country = form.value.country;
    this.request.addressDTO.houseNumber = form.value.houseNumber;
    this.request.addressDTO.street = form.value.street;
    this.request.ownerType = form.value.ownerType;
    this.request.email = form.value.email;
    this.request.password = form.value.password;
    this.request.phoneNumber = form.value.phoneNumber;
    this.request.registrationExplanation = form.value.registrationExplanation;
  }

  registeredSnackBar(){
    this.snackbar.open('Registration was successful!','cancel');
  }

}
