import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {CottageService} from '../service/cottage.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import * as bulmaCalendar from 'bulma-calendar';
import {MatDatepicker} from '@angular/material/datepicker';


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {
  cottage: CottageDTO = ({} as any);

  // Initialize all input of date type.


  cottageUpdateForm = this.formBuilder.group({
    cottageName: new FormControl(this.cottage.cottageName, Validators.required),
    description: new FormControl(this.cottage.description, Validators.required),
    pricePerDay: new FormControl(this.cottage.pricePerDay, [Validators.min(0), Validators.required]),
    maxNumberOfGuests: new FormControl(this.cottage.maxNumberOfGuests, [Validators.required, Validators.min(0)])
  });
  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private formBuilder: FormBuilder
  ) {
  }
    ngOnInit(): void {
    this.httpClient.get<any>(this.config.cottages_url + '/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.cottage = response;
      });
    // if (this.hasSignedIn() && this.loggedUserIsOwner()){
    //   // @ts-ignore
    //   this.cottageUpdateForm.get('cottageName')?.setValue(this.cottage.cottageName);
    // }
  }
  // tslint:disable-next-line:typedef
  hasRole(role: string){
    return this.userService.loggedRole(role);
  }
//   ngAfterViewInit(){
//     const calendars = bulmaCalendar.attach('[type="date"]');
//     // tslint:disable-next-line:prefer-for-of
//     // Loop on each calendar initialized
//     calendars.forEach(calendar => {
//       // Add listener to select event
//       calendar.on('select', date => {
//         console.log(date);
//       });
//     });
//
// // To access to bulmaCalendar instance of an element
//     const element = document.querySelector('input') as HTMLElement;
//     if (element) {
//       // bulmaCalendar instance is available as element.bulmaCalendar
//       element.bulmaCalendar.on('select', datepicker => {
//         console.log(datepicker.data.value());
//       });
//     }
//   }

  // tslint:disable-next-line:typedef
  hasSignedIn() {
    return !!this.userService.currentUser;
  }
  // NE MENJAJ OVO U ===. NECE RADITI!!!!!!!
  // tslint:disable-next-line:typedef
  loggedUserIsOwner(){
    return this.userService.currentUser.id == this.cottage.cottageOwnerId;
  }
  // tslint:disable-next-line:typedef
  deleteCottage(){
    // return this.httpClient.delete(this.config.cottages_url + '/' + this.cottage.id);
    // this.snackbar.open('cottage delete request sent', 'cancel');
    return this.cottageService.deleteCottage(this.cottage.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }
  // tslint:disable-next-line:typedef
  updateCottage(){
    // this.snackbar.open('cottage delete request sent', 'cancel');
    return this.cottageService.updateCottage(this.cottage).subscribe(
      res => {
        this.cottage = res;
      });
  }
}
