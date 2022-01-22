import { ChangePasswordComponent } from './profile/change-password/change-password.component';
import { InstructorGuard } from './guard/instructor.guard';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatSortModule} from '@angular/material/sort';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule} from '@angular/material/input';
import { MatButtonModule} from '@angular/material/button';
import { MatCardModule} from '@angular/material/card';
import { MatToolbarModule} from '@angular/material/toolbar';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule} from '@angular/material/sidenav';
import { NavbarComponent } from './navbar/navbar.component';
import { MatIconModule, MatIconRegistry} from '@angular/material/icon';
import { SignupComponent } from './signup/signup.component';
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
import { MatTableModule} from '@angular/material/table';
import { MatDialogModule} from '@angular/material/dialog';
import { MatSnackBarModule} from '@angular/material/snack-bar';
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
import { Ng2SearchPipeModule} from 'ng2-search-filter';
import { FilterPipe} from './user-quest/filter.pipe';
import { AdventuresUserComponent } from './user-quest/adventures-user/adventures-user.component';
import { UserComplaintComponent } from './user-complaint/user-complaint.component';
import { SubscriptionsComponent } from './user-quest/subscriptions/subscriptions.component';
import { ReservationsComponent } from './user-quest/reservations/reservations.component';
import { AngularYandexMapsModule , YaConfig} from 'angular8-yandex-maps';
import { MapComponent } from './map/map.component';
import { BoatsComponent } from './boats/boats.component';
import { CottagesComponent } from './cottages/cottages.component';
import { UserListComponent } from './user-list/user-list.component';
import { CottageComponent } from './cottage/cottage.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { RegisterCottageComponent } from './register-cottage/register-cottage.component';
import { MatGridListModule} from '@angular/material/grid-list';
import { DeleteProfileComponent } from './delete-profile/delete-profile.component';
import { DeleteRequestsComponent } from './delete-requests/delete-requests.component';
import { DeleteRequestResponseComponent } from './delete-request-response/delete-request-response.component';
import { BoatsUserComponent} from './user-quest/boats-user/boats-user.component';
import { CottagesUserComponent} from './user-quest/cottages-user/cottages-user.component';
import { OneCottageComponent } from './user-quest/cottages-user/one-cottage/one-cottage.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarComponent } from './calendar/calendar.component';
import { DatePipe} from '@angular/common';
import { ReservationFormComponent } from './reservation-form/reservation-form.component';
import { MatSlideToggleModule} from '@angular/material/slide-toggle';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from '@danielmoncada/angular-datetime-picker';
import { InstructorCalendarComponent } from './instructor-calendar/instructor-calendar.component';

import { OneAdventureComponent } from './user-quest/adventures-user/one-adventure/one-adventure.component';
import { OneBoatUserComponent } from './user-quest/boats-user/one-boat-user/one-boat-user.component';
import { CottageReservationsTableComponent } from './cottage/cottage-reservations/cottage-reservations-table/cottage-reservations-table.component';
import { InstructorAvailabilityFormComponent } from './instructor-availability-form/instructor-availability-form.component';
import { SignupAdminComponent } from './signup/signup-admin/signup-admin.component';
import { CalendarHeaderComponent } from './calendar/calendar-header/calendar-header/calendar-header.component';
import { OwnerReservationsComponent } from './owner-reservations/owner-reservations.component';
import { BoatComponent } from './boat/boat.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComplaintsComponent } from './complaints/complaints.component';
import { ClientReservationsComponent } from './client-reservations/client-reservations.component';
import {MatRadioModule} from '@angular/material/radio';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { EntityService } from './service/entity.service';
import { ReservationDialogComponent } from './client-reservations/reservation-dialog/reservation-dialog.component';
import { FastReservationComponent } from './fast-reservation/fast-reservation.component';
import { FastReservationDialogComponent } from './fast-reservation/fast-reservation-dialog/fast-reservation-dialog.component';
import { OwnerReviewFormComponent } from './owner-review-form/owner-review-form.component';
import { UserReservationsComponent } from './user-reservations/user-reservations.component';
import { FeedbackDialogComponent } from './user-reservations/feedback-dialog/feedback-dialog.component';
import { ClientsReviewsComponent } from './clients-reviews/clients-reviews.component';
import { ReviewDialogComponent } from './user-reservations/review-dialog/review-dialog.component';
import { FutureReservationsComponent } from './user-reservations/future-reservations/future-reservations.component';
import { OwnersReviewsComponent } from './owners-reviews/owners-reviews.component';
import { ConfigSingletonComponent } from './config-singleton/config-singleton.component';
import { RegisterBoatComponent } from './register-boat/register-boat.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationsHistoryComponent } from './client-reservations/reservations-history/reservations-history.component';
import { ClientGuard } from './guard/client.guard';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';


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
    InstructorCalendarComponent,
    OneAdventureComponent,
    OneBoatUserComponent,
    CottageReservationsTableComponent,
    InstructorAvailabilityFormComponent,
    SignupAdminComponent,
    CalendarHeaderComponent,
    OwnerReservationsComponent,
    BoatComponent,
    ComplaintsComponent,
    ClientReservationsComponent,
    ReservationDialogComponent,
    FastReservationComponent,
    FastReservationDialogComponent,
    OwnerReviewFormComponent,
    ClientsReviewsComponent,
    UserReservationsComponent,
    FeedbackDialogComponent,
    ReviewDialogComponent,
    FutureReservationsComponent,
    OwnersReviewsComponent,
    ConfigSingletonComponent,
    RegisterBoatComponent,
    DashboardComponent,
    ReservationsHistoryComponent
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
        CalendarModule.forRoot({provide: DateAdapter, useFactory: adapterFactory}),
        MatCheckboxModule,
        MatRadioModule,
        MatSortModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        NgbModule
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
    ClientGuard,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    EntityService,
    MatIconRegistry,
    MatNativeDateModule,
    MatDatepickerModule,
    DatePipe

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
