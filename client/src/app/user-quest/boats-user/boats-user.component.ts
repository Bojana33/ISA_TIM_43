import { Component, OnInit } from '@angular/core';
import {Boat} from '../../model/boat';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-boats-client',
  templateUrl: './boats-user.component.html',
  styleUrls: ['./boats-user.component.css']
})
export class BoatsUserComponent implements OnInit {

  allBoats: Boat[] = [];
  boats: Boat[] = [];
  boat: any;
  searchTerm: any;
  searchFilter: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getBoats();
  }

  // tslint:disable-next-line:typedef
  getBoats(){
    this.httpClient.get<any>(this.config.boat_url + '/get_all').subscribe(
      (data) => {
        console.log(data);
        this.boats = data;
        this.allBoats = this.boats;
      });
  }

  search(search: any, value: any): void {
    if (search === 'name') {
      this.boats = this.allBoats.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    } else if (search === 'pricePerDay') {
      this.boats = this.allBoats.filter((val) => Number(val.pricePerDay) === Number(value));
    } else if (search === 'averageGrade') {
      this.boats = this.allBoats.filter((val) => Number(val.averageGrade) === Number(value));
    }
    //else if (search === 'location') {
    //this.cottages = this.allCottages.filter((val) => val.address.includes(value) || val.name.toLowerCase().includes(value));
    //}
  }

  sort(criterion: any) {
    if(criterion === 'name')
      this.allBoats.sort((a,b) => a.name > b.name ? 1 : -1);
    else if(criterion === 'price')
      this.allBoats.sort((a, b) => a.pricePerDay > b.pricePerDay ? 1 : -1);
    else if(criterion === 'location')
      this.allBoats.sort(function(a, b) {
        if (a.address.country !== b.address.country) {
          // Price is only important when cities are the same
          return a.address.country > b.address.country ? 1 : -1;
        }
        if (a.address.city !== b.address.city) {
          return a.address.city > b.address.city ? 1 : -1;
        }
        else if (a.address.street !== b.address.street){
          return a.address.street > b.address.street ? 1 : -1;
        }
        else
          return a.address.houseNumber > b.address.houseNumber ? 1 : -1;
      });
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }
}
