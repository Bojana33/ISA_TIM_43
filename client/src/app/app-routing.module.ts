import { RejectRequestComponent } from './reject-request/reject-request.component';
import { GuestGuard } from './guard/guest.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {SignupClientComponent} from './signup/signup-client/signup-client.component';
import { AdminGuard } from './guard/admin.guard';
import { AdminComponent } from './admin/admin.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { LoginGuard } from './guard/login.guard';
import { NotFoundComponent } from './not-found/not-found.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { RegistrationRequestsComponent } from './registration-requests/registration-requests.component';
import { ProfileComponent } from './profile/profile.component';
import {UserService} from './service/user.service';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    pathMatch: 'full'
  },

  {
    path: 'signupClient',
    component: SignupClientComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'signup',
    component: SignupComponent,
    canActivate: [GuestGuard],
    pathMatch: 'full'
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [UserService],
    pathMatch: 'full'
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'registration-requests',
    component: RegistrationRequestsComponent
  },
  {
    path: 'reject-request/:id',
    component: RejectRequestComponent
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    canActivate: [LoginGuard]
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '403',
    component: ForbiddenComponent
  }
  // {
  //   path: '**',
  //   redirectTo: '/404'
  // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
