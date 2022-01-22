import { Adventure } from './../model/adventure';
import { UserService } from './../service/user.service';
import { ApiService } from './../service/api.service';
import { AdditionalService } from './../model/additional-service';
import { Address } from './../model/address';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ConfigService } from '../service/config.service';

@Component({
  selector: 'app-adventures',
  templateUrl: './adventures.component.html',
  styleUrls: ['./adventures.component.css']
})
export class AdventuresComponent implements OnInit {

  adventures: Adventure[]=[];
  allAdventures: Adventure[] = [];
  name:any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private api: ApiService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getAdventures();
  }

  getAdventures(){
    this.api.get(this.config.adventure_url + '/get_my_adventures/'+ this.userService.currentUser.id).subscribe(
      response => {
        console.log(response);
        this.adventures = response;
        this.allAdventures = this.adventures;
      }
    )
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  filterAdventures(adventureName: any){
    this.adventures = this.allAdventures.filter((val) => val.name.toUpperCase().includes(adventureName)
      || val.name.toLowerCase().includes(adventureName));
  }

}
