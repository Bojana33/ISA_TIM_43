import { Component, OnInit } from '@angular/core';
import {BoatService} from '../service/boatService/boat.service';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {BoatDTO} from '../model/boat-dto';

@Component({
  selector: 'app-boats',
  templateUrl: './boats.component.html',
  styleUrls: ['./boats.component.css']
})
export class BoatsComponent implements OnInit {
  allBoats: BoatDTO[] = [];


  constructor(private boatService: BoatService,
              private httpClient: HttpClient,
              private config: ConfigService,
  ) {
  }

  ngOnInit(): void {
    this.httpClient.get<any>(this.config.boat_url + '/get_all').subscribe(results => {
      this.allBoats = results;
    });
  }
  filterBoats(boatName: string): void{
    this.allBoats.filter((boat) => boat.name.includes(boatName));
  }

}
