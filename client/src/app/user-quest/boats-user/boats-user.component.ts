import { Component, OnInit } from '@angular/core';
import {Boat} from '../../model/boat';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';

@Component({
  selector: 'app-boats',
  templateUrl: './boats.component.html',
  styleUrls: ['./boats.component.css']
})
export class BoatsComponent implements OnInit {

  allBoats: Boat[] = [];
  boats: Boat[] = [];
  boat: any;
  searchTerm: any;
  searchFilter: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
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
}
