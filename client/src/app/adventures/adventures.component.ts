import { ApiService } from './../service/api.service';
import { AdditionalService } from './../model/additional-service';
import { Address } from './../model/address';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ConfigService } from '../service/config.service';
import { Adventure } from '../model/adventure';

@Component({
  selector: 'app-adventures',
  templateUrl: './adventures.component.html',
  styleUrls: ['./adventures.component.css']
})
export class AdventuresComponent implements OnInit {

  adventures: Adventure[]=[];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private api: ApiService
  ) { }

  ngOnInit(): void {
    this.getAdventures();
  }

  getAdventures(){
    this.api.get(this.config.adventure_url + '/get_all_adventures').subscribe(
      response => {
        console.log(response);
        this.adventures = response;
      }
        
    )
  }

}
