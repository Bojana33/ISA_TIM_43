import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CottageService} from '../../../service/cottage.service';
import {AdventureDTO} from '../../../model/adventure-dto';
import { EntityService } from 'src/app/service/entity.service';
import {UserDTO} from "../../../model/user-dto";
import {AdditionalServicesDTO} from "../../../model/AdditionalServicesModel/additional-services-dto.model";

@Component({
  selector: 'app-one-adventure',
  templateUrl: './one-adventure.component.html',
  styleUrls: ['./one-adventure.component.css']
})
export class OneAdventureComponent implements OnInit {

  adventure: AdventureDTO = ({} as any);
  isSubscribed!: boolean;
  instructor: UserDTO = ({} as any);
  checkboxFlag: AdditionalServicesDTO[] = [];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private formBuilder: FormBuilder,
    private entityService: EntityService,
    private snackBar: MatSnackBar
  ) {
  }

  ngOnInit(): void {
    console.log(this.router.snapshot.params.id);
    this.httpClient.get<any>(this.config.adventure_url + '/get_adventure/' + this.router.snapshot.params.id).subscribe(
      response => {
        console.log(response);
        this.adventure = response;
        this.getInstructorInfo(response.adventureOwnerId);
      });
    this.entityService.isSubscribed(this.router.snapshot.params.id).subscribe(res =>
    {
      this.isSubscribed = res;
    })
  }

  subscribe(adventureId: any){
    this.entityService.subscribeToEntity(adventureId).subscribe();
    this.isSubscribed = true;
  }

  getInstructorInfo(id: any){
    this.userService.getUser(id).subscribe(res => {
      console.log(res);
      this.instructor = res;
    });
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  addAdditionalService(serv: AdditionalServicesDTO){
    this.checkboxFlag.push(serv);
    console.log(this.checkboxFlag);
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }
}
