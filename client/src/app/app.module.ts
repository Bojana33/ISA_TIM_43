import { InstructorGuard } from './guard/instructor.guard';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatSidenavModule} from '@angular/material/sidenav';
import { NavbarComponent } from './navbar/navbar.component';
import {MatIconModule, MatIconRegistry} from '@angular/material/icon';
import { SignupComponent } from './signup/signup.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ApiService } from './service/api.service';
import { UserService } from './service/user.service';
import { ConfigService } from './service/config.service';
import { AuthService } from './service/auth.service';
import { LoginGuard } from './guard/login.guard';
import { GuestGuard } from './guard/guest.guard';
import { AdminGuard } from './guard/admin.guard';
import { TokenInterceptor } from './interceptor/TokenInterceptor';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SignupClientComponent } from './signup/signup-client/signup-client.component';
import { MatSelectModule} from '@angular/material/select';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { RegistrationRequestsComponent } from './registration-requests/registration-requests.component';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { RejectRequestComponent } from './reject-request/reject-request.component';
import { ProfileComponent } from './profile/profile.component';
import { AdventuresComponent } from './adventures/adventures.component';
import { AdventureComponent } from './adventure/adventure.component';
import { UpdateAdventureComponent } from './update-adventure/update-adventure.component';
import { CreateAdventureComponent } from './create-adventure/create-adventure.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRippleModule } from '@angular/material/core';
import { ProfileUpdateComponent } from './profile/profile-update/profile-update.component';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {FilterPipe} from './user-quest/filter.pipe';
import { AdventuresUserComponent } from './user-quest/adventures-user/adventures-user.component';
import { UserComplaintComponent } from './user-complaint/user-complaint.component';
import { SubscriptionsComponent } from './user-quest/subscriptions/subscriptions.component';
import { ReservationsComponent } from './user-quest/reservations/reservations.component';
// import { AngularYandexMapsModule , YaConfig} from 'angular8-yandex-maps';
import { AngularYandexMapsModule , YaConfig} from 'angular8-yandex-maps';
import { MapComponent } from './map/map.component';
import { BoatsComponent } from './boats/boats.component';
import { CottagesComponent } from './cottages/cottages.component';
import { UserListComponent } from './user-list/user-list.component';
import { CottageComponent } from './cottage/cottage.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { RegisterCottageComponent } from './register-cottage/register-cottage.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { DeleteProfileComponent } from './delete-profile/delete-profile.component';
import { DeleteRequestsComponent } from './delete-requests/delete-requests.component';
import { DeleteRequestResponseComponent } from './delete-request-response/delete-request-response.component';
import {BoatsUserComponent} from './user-quest/boats-user/boats-user.component';
import {CottagesUserComponent} from './user-quest/cottages-user/cottages-user.component';
import { OneCottageComponent } from './user-quest/cottages-user/one-cottage/one-cottage.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarComponent } from './calendar/calendar.component';
import {DatePipe} from '@angular/common';
import { ReservationFormComponent } from './reservation-form/reservation-form.component';

import { OwlDateTimeModule, OwlNativeDateTimeModule } from '@danielmoncada/angular-datetime-picker';
import { OneAdventureComponent } from './user-quest/adventures-user/one-adventure/one-adventure.component';
import { OneBoatUserComponent } from './user-quest/boats-user/one-boat-user/one-boat-user.component';


const mapConfig: YaConfig = {
  apikey: 'cb834c63-c138-4b32-a96f-8e5b8427de81',
  lang: 'en_US',
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    SignupComponent,
    ChangePasswordComponent,
    ForbiddenComponent,
    NotFoundComponent,
    SignupClientComponent,
    AdminComponent,
    HomeComponent,
    RegistrationRequestsComponent,
    RejectRequestComponent,
    ProfileComponent,
    AdventuresComponent,
    AdventureComponent,
    ProfileComponent,
    UpdateAdventureComponent,
    CreateAdventureComponent,
    ProfileUpdateComponent,
    CottagesComponent,
    FilterPipe,
    BoatsComponent,
    AdventuresUserComponent,
    UserComplaintComponent,
    SubscriptionsComponent,
    ReservationsComponent,
    MapComponent,
    BoatsComponent,
    CottagesComponent,
    AdventuresComponent,
    UserListComponent,
    CottageComponent,
    AddressFormComponent,
    RegisterCottageComponent,
    UserListComponent,
    DeleteProfileComponent,
    DeleteRequestsComponent,
    DeleteRequestResponseComponent,
    BoatsUserComponent,
    CottagesUserComponent,
    OneCottageComponent,
    CalendarComponent,
    ReservationFormComponent,
    OneAdventureComponent,
    OneBoatUserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatIconModule,
    MatSelectModule,
    MatTableModule,
    MatDialogModule,
    MatSnackBarModule,
    MatNativeDateModule,
    MatRippleModule,
    Ng2SearchPipeModule,
    MatGridListModule,
    AngularYandexMapsModule.forRoot(mapConfig),
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatButtonModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    AngularYandexMapsModule.forRoot(mapConfig),
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })
  ],
  exports: [
    SignupClientComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    LoginGuard,
    GuestGuard,
    InstructorGuard,
    AdminGuard,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    MatIconRegistry,
    MatNativeDateModule,
    MatDatepickerModule,
    DatePipe

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
