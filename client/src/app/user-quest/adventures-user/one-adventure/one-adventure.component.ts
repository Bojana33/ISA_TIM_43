import { AdventureService } from './../../../service/adventure.service';
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CottageService} from '../../../service/cottage.service';
import {AdventureDTO} from '../../../model/adventure-dto';
import { EntityService } from 'src/app/service/entity.service';

@Component({
  selector: 'app-one-adventure',
  templateUrl: './one-adventure.component.html',
  styleUrls: ['./one-adventure.component.css']
})
export class OneAdventureComponent implements OnInit {

  adventure: AdventureDTO = ({} as any);
  isSubscribed!: boolean;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private route: Router,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private formBuilder: FormBuilder,
    private entityService: EntityService,
    private snackBar: MatSnackBar,
    private adventureService: AdventureService
  ) {
  }

  ngOnInit(): void {
    console.log(this.router.snapshot.params.id);
    this.httpClient.get<any>(this.config.adventure_url + '/get_adventure/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.adventure = response;
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

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }
  deleteAdventure(){
    return this.adventureService.deleteAdventure(this.adventure.id).subscribe(res=>{console.log(res); this.snackbar.open('Adventure deleted', 'cancel'); this.route.navigate(['/adventuresCatalog'])});
  }
}
