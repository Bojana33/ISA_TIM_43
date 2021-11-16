import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {SignupClientComponent} from './signup/signup-client/signup-client.component';

const routes: Routes = [
  {path:'login', component: LoginComponent },
  {path:'signup', component: SignupComponent },
  {path:'signupClient', component: SignupClientComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
