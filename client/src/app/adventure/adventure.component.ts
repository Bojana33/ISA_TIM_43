import { AdventureService } from './../service/adventure.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../service/config.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-adventure',
  templateUrl: './adventure.component.html',
  styleUrls: ['./adventure.component.css']
})
export class AdventureComponent implements OnInit {

  adventure:any;
  
  address!: string;
  addressTxt! : string;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private adventureService:AdventureService
  ) { }

  ngOnInit(): void {
    this.getAdventure();
  }
  urls:string[] = [];

  getAdventure(){
    return this.httpClient.get(this.config.adventure_url + '/get_adventure/' + this.router.snapshot.params.id)
    .subscribe((response) =>{
      this.adventure = response;
      this.address = this.adventure.address.country + ' ' + this.adventure.address.city + ' '  + this.adventure.address.street + '&kind=house&results=' + this.adventure.address.houseNumber;
      this.addressTxt = this.adventure.address.country + ' ' + this.adventure.address.city + ' '  + this.adventure.address.street + ' '+ this.adventure.address.houseNumber;
    });
  }
  
  hasRole(role:string){
    return this.userService.loggedRole(role);
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  onSelect(event:any){
    if(event.target.files){
      for(let i=0; i<event.target.files.length;i++){
        var reader = new FileReader();
        reader.readAsDataURL(event.target.files[i]);
        reader.onload=(events:any)=>{
          this.urls.push(events.target.result as string);
        }
      }
    }
  }

  deleteAdventure(){
    return this.adventureService.deleteAdventure(this.adventure.id);
  }

}
