import { UserService } from './../../service/user.service';
import { PasswordChanger } from './../../model/password-changer';
import { AuthService } from './../../service/auth.service';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomValidationService } from 'src/app/service/custom-validation.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Input() showForm: any;
  changePasswordForm!: FormGroup;
  passwordChanger!: PasswordChanger;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private customValidator: CustomValidationService,
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar,
  ) { }

  ngOnInit(): void {

    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      newPassword: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      repeatNewPassword: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(64)])]
    },
    {validator: this.customValidator.passwordMatchValidator('newPassword', 'repeatNewPassword')});
  }

  saveChangedPassword(){
    this.submitted = true;
    this.setPasswordChanger(this.changePasswordForm);
    return this.authService.changePassowrd(this.passwordChanger)
      .subscribe(
        () => {
        this.authService.logout()
        this.router.navigate(['/login']);
        this.snackbar.open('Success! Please sign in with your new password.', 'cancel')
      }, error => {
        this.submitted = false;
        this.snackbar.open('Invalid old password.', 'cancel')
      });
  }

  setPasswordChanger(form:any){
    this.passwordChanger = new PasswordChanger();
    this.passwordChanger.oldPassword = form.value.oldPassword;
    this.passwordChanger.newPassword = form.value.newPassword;
  }

}
