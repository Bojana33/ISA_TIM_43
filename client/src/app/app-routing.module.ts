import { UpdateAdventureComponent } from './update-adventure/update-adventure.component';
import { ProfileComponent } from './profile/profile.component';
import { InstructorGuard } from './guard/instructor.guard';
import { AdventuresComponent } from './adventures/adventures.component';
import { RejectRequestComponent } from './reject-request/reject-request.component';
import { GuestGuard } from './guard/guest.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
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
import { AdventureComponent } from './adventure/adventure.component';
import { Adventure } from './model/adventure';
import { CreateAdventureComponent } from './create-adventure/create-adventure.component';
import {BoatsComponent} from './boats/boats.component';
import {CottagesComponent} from './cottages/cottages.component';
import {UserListComponent} from './user-list/user-list.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'signupClient',
    component: SignupClientComponent
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
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'adventures',
    component: AdventuresComponent,
    // canActivate: [InstructorGuard]
  },
  {
    path: 'adventure/:id',
    component: AdventureComponent
  },
  {
    path:'update-adventure/:id',
    component: UpdateAdventureComponent,
    //canActivate: [InstructorGuard]
  },
  {
    path:'create-adventure',
    component: CreateAdventureComponent,
    //canActivate: [InstructorGuard]
  },
  {
    path: 'boats',
    component: BoatsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'cottages',
    component: CottagesComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'users',
    component: UserListComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'registration-requests',
    component: RegistrationRequestsComponent,
    canActivate: [AdminGuard]
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
