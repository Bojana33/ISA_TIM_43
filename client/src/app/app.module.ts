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
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
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
import { AdventuresComponent } from './adventures/adventures.component';
import { AdventureComponent } from './adventure/adventure.component';
import { ProfileComponent } from './profile/profile.component';
import { UpdateAdventureComponent } from './update-adventure/update-adventure.component';
import { CreateAdventureComponent } from './create-adventure/create-adventure.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRippleModule } from '@angular/material/core';
import { AngularYandexMapsModule , YaConfig} from 'angular8-yandex-maps';
import { MapComponent } from './map/map.component';
import { BoatsComponent } from './boats/boats.component';
import { CottagesComponent } from './cottages/cottages.component';
import { UserListComponent } from './user-list/user-list.component';
import {MatGridListModule} from '@angular/material/grid-list';


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
    AdventuresComponent,
    AdventureComponent,
    ProfileComponent,
    UpdateAdventureComponent,
    CreateAdventureComponent,
    MapComponent,
    BoatsComponent,
    CottagesComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
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
    MatDatepickerModule,
    MatNativeDateModule,
    MatRippleModule,
    MatGridListModule,
    AngularYandexMapsModule.forRoot(mapConfig)
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
    MatNativeDateModule
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
