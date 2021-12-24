import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageService} from '../service/cottage.service';
import {CottageDTO} from '../model/cottage-dto.model';
import {ActivatedRoute} from '@angular/router';
import {ConfigService} from '../service/config.service';
import {ApiService} from '../service/api.service';

@Component({
  selector: 'app-cottages',
  templateUrl: './cottages.component.html',
  styleUrls: ['./cottages.component.css']
})
export class CottagesComponent implements OnInit {
  cottages: CottageDTO[] = [];


  constructor(private cottageService: CottageService,
              private httpClient: HttpClient,
              private config: ConfigService
  ) {
  }

  ngOnInit(): void {
    this.httpClient.get<any>(this.config.cottages_url).subscribe(results => {
      console.log(results);
      this.cottages = results;
    });
  }

  // tslint:disable-next-line:typedef
  filterCottages(cottageName: string){
    return this.httpClient.get<CottageDTO[]>(this.config.cottages_url, {params: {cottageName}}).subscribe(results => {
      this.cottages = results;
    });
  }

}
