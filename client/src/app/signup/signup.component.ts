import { UserType } from './../enum/user-type';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  form!: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar,
    private http: HttpClient
    ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: '',
      surname: '',
      email: '',
      password: '',
      repeatPassword: '',
      address: '',
      city: '',
      country: '',
      phoneNumber: '',
      registrationExplanation: '',
      userType: UserType
    });
  }

  submit() : void{
    console.log();
    this.http.post('http://localhost:8090/auth/signup',this.form.getRawValue())
    .subscribe(res =>{
      console.log(res);
    });
  }

  registeredSnackBar(){
    this.snackbar.open('Registration was successful!','cancel');
  }

}
