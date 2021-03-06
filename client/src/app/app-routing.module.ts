import { OwnerReservationsComponent } from './owner-reservations/owner-reservations.component';
import { DeleteProfileComponent } from './delete-profile/delete-profile.component';
import { UpdateAdventureComponent } from './update-adventure/update-adventure.component';
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
import { ChangePasswordComponent } from './profile/change-password/change-password.component';
import { LoginGuard } from './guard/login.guard';
import { NotFoundComponent } from './not-found/not-found.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { RegistrationRequestsComponent } from './registration-requests/registration-requests.component';
import { ProfileComponent } from './profile/profile.component';
import {UserService} from './service/user.service';
import { AdventureComponent } from './adventure/adventure.component';
import { Adventure } from './model/adventure';
import { CreateAdventureComponent } from './create-adventure/create-adventure.component';
import {ProfileUpdateComponent} from './profile/profile-update/profile-update.component';
import {AdventuresUserComponent} from './user-quest/adventures-user/adventures-user.component';
import {UserComplaintComponent} from './user-complaint/user-complaint.component';
import {ReservationsComponent} from './user-quest/reservations/reservations.component';
import {BoatsComponent} from './boats/boats.component';
import {CottagesComponent} from './cottages/cottages.component';
import {UserListComponent} from './user-list/user-list.component';
import {CottageComponent} from './cottage/cottage.component';
import {ReactiveFormsModule} from '@angular/forms';
import {RegisterCottageComponent} from './register-cottage/register-cottage.component';
import {MatSelectModule} from '@angular/material/select';
import {BrowserModule} from '@angular/platform-browser';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { DeleteRequestsComponent } from './delete-requests/delete-requests.component';
import { DeleteRequestResponseComponent } from './delete-request-response/delete-request-response.component';
import {BoatsUserComponent} from './user-quest/boats-user/boats-user.component';
import {CottagesUserComponent} from './user-quest/cottages-user/cottages-user.component';
import {OneCottageComponent} from './user-quest/cottages-user/one-cottage/one-cottage.component';
import {CalendarComponent} from './calendar/calendar.component';
import {OneAdventureComponent} from './user-quest/adventures-user/one-adventure/one-adventure.component';
import {OneBoatUserComponent} from './user-quest/boats-user/one-boat-user/one-boat-user.component';
import {CottageReservationsTableComponent} from './cottage/cottage-reservations/cottage-reservations-table/cottage-reservations-table.component';
import { SignupAdminComponent } from './signup/signup-admin/signup-admin.component';
import {Boat} from './model/boat';
import {BoatComponent} from './boat/boat.component';
import { ComplaintsComponent } from './complaints/complaints.component';
import {CottageownerGuard} from './guard/cottageowner.guard';
import {BoatownerGuard} from './guard/boatowner.guard';
import {RegisterBoatComponent} from './register-boat/register-boat.component';
import {DashboardComponent} from './dashboard/dashboard.component';

import {ReservationFormComponent} from './reservation-form/reservation-form.component';
import {ClientReservationsComponent} from './client-reservations/client-reservations.component';
import {FastReservationComponent} from './fast-reservation/fast-reservation.component';
import {UserReservationsComponent} from './user-reservations/user-reservations.component';
import { ClientsReviewsComponent } from './clients-reviews/clients-reviews.component';
import { FutureReservationsComponent } from './user-reservations/future-reservations/future-reservations.component';
import { OwnersReviewsComponent } from './owners-reviews/owners-reviews.component';
import { ConfigSingletonComponent } from './config-singleton/config-singleton.component';
import {SubscriptionsComponent} from './user-quest/subscriptions/subscriptions.component';
import {CottageOwnerAndBoatOwnerGuard} from './guard/cottage-owner-and-boat-owner.guard';
import {ReservationsHistoryComponent} from "./client-reservations/reservations-history/reservations-history.component";
import {ClientGuard} from "./guard/client.guard";


const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'reservationForm',
    component: ReservationFormComponent,
    pathMatch: 'full'
  },
  {
    path: 'createReservation',
    component: ClientReservationsComponent
  },
  {
    path: 'signupClient',
    component: SignupClientComponent,
    canActivate: [GuestGuard],
    pathMatch: 'full'
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
    path: 'signupAdmin',
    component: SignupAdminComponent,
    canActivate: [AdminGuard, GuestGuard]
  },
  {
    path: 'userReservations',
    component: UserReservationsComponent
  },
  {
    path: 'reservationsHistory',
    component: ReservationsHistoryComponent,
    //canActivate: [ClientGuard]
    pathMatch: 'full'
  },
  {
    path: 'userProfile',
    component: ProfileComponent,
    canActivate: [LoginGuard, GuestGuard],
    pathMatch: 'full'
  },
  {
    path: 'profile/:id',
    component: ProfileComponent,
    canActivate: [LoginGuard, GuestGuard],
    pathMatch: 'full'
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'fastReservation',
    component: FastReservationComponent
  },
  {
    path: 'subscriptions',
    component: SubscriptionsComponent
  },
  {
    path: 'futureReservations',
    component: FutureReservationsComponent
  },
  {
    path: 'profile/:id/profileUpdate',
    component: ProfileUpdateComponent,
    canActivate: [LoginGuard, GuestGuard]
  },
  {
    path: 'adventures',
    component: AdventuresComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'adventure/:id',
    component: AdventureComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'update-adventure/:id',
    component: UpdateAdventureComponent,
    canActivate: [InstructorGuard, GuestGuard]
  },
  {
    path: 'create-adventure',
    component: CreateAdventureComponent,
    canActivate: [InstructorGuard, GuestGuard]
  },
  // {
  //  path: 'boats',
 //   component: BoatsUserComponent,
  //  canActivate: [GuestGuard]
 // },
  {
    path: 'cottages',
    component: CottagesComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'boats',
    component: BoatsComponent
  },
  {
    path: 'users',
    component: UserListComponent,
    canActivate: [ GuestGuard]
  },
  {
    path: 'registration-requests',
    component: RegistrationRequestsComponent,
    canActivate: [AdminGuard, GuestGuard]
  },
  {
    path: 'reject-request/:id',
    component: RejectRequestComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'cottages/:id',
    component: CottageComponent,
    canActivate: [CottageownerGuard]
  },
  {
    path: 'boats/:id',
    component: BoatComponent,
    canActivate: [BoatownerGuard]
  },
  {
    path: 'cottages/:id/reservations',
    component: CottageReservationsTableComponent,
    canActivate: [CottageOwnerAndBoatOwnerGuard]
  },
  {
    path: 'boats/:id/reservations',
    component: CottageReservationsTableComponent,
    canActivate: [CottageOwnerAndBoatOwnerGuard]
  },
  {
    path: 'calendar',
    component: CalendarComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'register-cottage',
    component: RegisterCottageComponent,
    canActivate: [CottageownerGuard]
  },
  {
    path: 'register-boat',
    component: RegisterBoatComponent,
    canActivate: [BoatownerGuard]
  },
  {
    path: 'delete-profile',
    component: DeleteProfileComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [CottageOwnerAndBoatOwnerGuard]
  },
  {
    path: 'user-delete-requests',
    component: DeleteRequestsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'delete-request-response/:id/:isApproved',
    component: DeleteRequestResponseComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent
  },
  {
    path: 'cottagesCatalog',
    component: CottagesUserComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'oneCottage/:id',
    component: OneCottageComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'oneAdventure/:id',
    component: OneAdventureComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'oneBoat/:id',
    component: OneBoatUserComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'boatsCatalog',
    component: BoatsUserComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'adventuresCatalog',
    component: AdventuresUserComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'userComplaint',
    component: UserComplaintComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'complaints',
    component: ComplaintsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'userReservations',
    component: ReservationsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'owner-reservations/:id',
    component: OwnerReservationsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'clients-reviews',
    component: ClientsReviewsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'owners-reviews',
    component: OwnersReviewsComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'config-singleton',
    component: ConfigSingletonComponent,
    canActivate: [GuestGuard]
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '403',
    component: ForbiddenComponent
  },
  { path: '**', redirectTo: '/home', pathMatch: 'full' }

  // {
  //   path: '**',
  //   redirectTo: '/404'
  // }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    BrowserModule,
    RouterModule,
    // MaterialModule,
    MatSelectModule,
    AngularYandexMapsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
