import { Component, OnInit } from '@angular/core';
import {Adventure} from '../model/adventure';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {Boat} from '../model/boat';
import {Cottage} from '../model/cottage';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  cottages: Cottage[] = [];
  adventures: Adventure[] = [];
  boats: Boat[] = [];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) { }

  ngOnInit(): void {
    this.getAdventures();
    this.getBoats();
    this.getCottages();
  }

  // tslint:disable-next-line:typedef
  getAdventures(){
    this.httpClient.get<any>(this.config.adventure_url + '/get_all_adventures').subscribe(
      (data) => {
        console.log(data);
        this.adventures = data;
      });
  }

  getBoats(){
    this.httpClient.get<any>(this.config.boat_url + '/get_all').subscribe(
      (data) => {
        this.boats = data;
      });
  }

  getCottages(){
    this.httpClient.get<any>(this.config.cottage_url + '/get_all').subscribe(
      (data) => {
        this.cottages = data;
      });
  }
}
