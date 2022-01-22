import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {Adventure} from '../../model/adventure';
import { AdventureDTO } from 'src/app/model/adventure-dto';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-adventures-user',
  templateUrl: './adventures-user.component.html',
  styleUrls: ['./adventures-user.component.css']
})
export class AdventuresUserComponent implements OnInit {

  allAdventures: AdventureDTO[] = [];
  adventures: AdventureDTO[] = [];
  adventure: any;
  searchTerm: any;
  searchFilter: any;
  addressTxt!: string;
  address!: string;
  tmpAdventures!: AdventureDTO[];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.getAdventures();
  }

  // tslint:disable-next-line:typedef
  getAdventures(){
    this.httpClient.get<any>(this.config.adventure_url + '/get_all_adventures').subscribe(
      (data) => {
        console.log(data);
        this.adventures = data;
        this.allAdventures = this.adventures;
        for(let adventure of this.allAdventures){
          this.userService.getUser(adventure.adventureOwnerId).subscribe(res => {
            console.log(res);
            adventure.instructorName =  res.firstName;
            this.tmpAdventures.push(adventure);;
          });
        }
        this.allAdventures = this.tmpAdventures;
      });
  }

  search(search: any, value: any): void {
    if (search === 'name') {
      this.adventure = this.allAdventures.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    } else if (search === 'pricePerDay') {
      this.adventure = this.allAdventures.filter((val) => Number(val.pricePerDay) === Number(value));
    } else if (search === 'averageGrade') {
      this.adventure = this.allAdventures.filter((val) => Number(val.averageGrade) === Number(value));
    }
    // else if (search === 'location') {
    // this.cottages = this.allCottages.filter((val) => val.address.includes(value) || val.name.toLowerCase().includes(value));
    // }
    //else if (search === 'instructorName') {
      //this.adventure = this.allAdventures.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    //}
  }

  sort(criterion: any) {
    if(criterion === 'name')
      this.allAdventures.sort((a,b) => a.name > b.name ? 1 : -1);
    else if (criterion === 'instructorName')
      this.allAdventures.sort((a, b) => a.instructorName > b.instructorName ? 1 : -1);
    else if(criterion === 'price')
      this.allAdventures.sort((a, b) => a.pricePerDay > b.pricePerDay ? 1 : -1);
    else if(criterion === 'location')
      this.allAdventures.sort(function(a, b) {
        if (a.addressDTO.country !== b.addressDTO.country) {
          // Price is only important when cities are the same
          return a.addressDTO.country > b.addressDTO.country ? 1 : -1;
        }
        if (a.addressDTO.city !== b.addressDTO.city) {
          return a.addressDTO.city > b.addressDTO.city ? 1 : -1;
        }
        else if (a.addressDTO.street !== b.addressDTO.street){
          return a.addressDTO.street > b.addressDTO.street ? 1 : -1;
        }
        else
          return a.addressDTO.houseNumber > b.addressDTO.houseNumber ? 1 : -1;
      });
  }
}
