import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {Adventure} from '../../model/adventure';

@Component({
  selector: 'app-adventures-user',
  templateUrl: './adventures-user.component.html',
  styleUrls: ['./adventures-user.component.css']
})
export class AdventuresUserComponent implements OnInit {

  allAdventures: Adventure[] = [];
  adventures: Adventure[] = [];
  adventure: any;
  searchTerm: any;
  searchFilter: any;
  addressTxt!: string;
  address!: string;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) { }

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
        console.log(this.addressTxt);
      });
  }

  search(search: any, value: any): void {
    if (search === 'name') {
      this.adventure = this.allAdventures.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    } else if (search === 'pricePerDay') {
      this.adventure = this.allAdventures.filter((val) => Number(val.pricePerDay) === Number(value));
    } //else if (search === 'averageGrade') {
     // this.adventure = this.allAdventures.filter((val) => Number(val.averageGrade) === Number(value));
    //}
    // else if (search === 'location') {
    // this.cottages = this.allCottages.filter((val) => val.address.includes(value) || val.name.toLowerCase().includes(value));
    // }
    else if (search === 'instructorName') {
      this.adventure = this.allAdventures.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    }
  }

}
