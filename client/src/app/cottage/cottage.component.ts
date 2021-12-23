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


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {
  cottage: CottageDTO = ({} as any);

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
  }
  // tslint:disable-next-line:typedef
  hasRole(role: string){
    return this.userService.loggedRole(role);
  }

  // tslint:disable-next-line:typedef
  hasSignedIn() {
    return !!this.userService.currentUser;
  }
  // tslint:disable-next-line:typedef
  loggedUserIsOwner(){
    return this.userService.currentUser.id === String(this.cottage.cottageOwnerId);
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
