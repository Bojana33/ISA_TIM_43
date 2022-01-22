import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OwnerType } from 'src/app/enum/owner-type';
import { RegistrationRequest } from 'src/app/model/registration-request';
import { UserRequest } from 'src/app/model/user-request';
import { CustomValidationService } from 'src/app/service/custom-validation.service';
import { RegistrationRequestService } from 'src/app/service/registration-request.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-signup-admin',
  templateUrl: './signup-admin.component.html',
  styleUrls: ['./signup-admin.component.css']
})
export class SignupAdminComponent implements OnInit {

  form!: FormGroup;
  request!: UserRequest;

  constructor(
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar,
    private customValidator: CustomValidationService,
    private requestService: UserService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        firstName: '',
        surname: '',
        email: '',
        street: '',
        city: '',
        country: '',
        houseNumber: '',
        phoneNumber: '',
        registrationExplanation: '',
        ownerType: OwnerType
      });
  }

  submit() : void{
    console.log();
    this.saveAdmin(this.form);
    this.requestService.saveAdmin(this.request).subscribe(res =>{console.log(res);this.registeredSnackBar();
    } , error => {
      this.snackbar.open('Admin registration failed', 'cancel');
    });
  }

  saveAdmin(form:any){
    this.request = new UserRequest();
    this.request.firstName = form.value.firstName;
    this.request.surname = form.value.surname;
    this.request.addressDTO.city = form.value.city;
    this.request.addressDTO.country = form.value.country;
    this.request.addressDTO.houseNumber = form.value.houseNumber;
    this.request.addressDTO.street = form.value.street;
    this.request.email = form.value.email;
    this.request.phoneNumber = form.value.phoneNumber;
  }

  registeredSnackBar(){
    this.snackbar.open('Registration was successful!','cancel');
  }

}
