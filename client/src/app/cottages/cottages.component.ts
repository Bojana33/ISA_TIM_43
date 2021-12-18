import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageService} from '../service/cottage.service';
import {CottageDTO} from '../model/cottage-dto.model';

@Component({
  selector: 'app-cottages',
  templateUrl: './cottages.component.html',
  styleUrls: ['./cottages.component.css']
})
export class CottagesComponent implements OnInit {
  cottages: CottageDTO[] = [];


  constructor(private cottageService: CottageService,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get<any>('http://localhost:8090/cottages').subscribe(results => {
      console.log(results);
      this.cottages = results;
    });
    getCottageById(id:number):void {
    return this.httpClient.get('http://localhost:8090/cottages' +  id)
      .subscribe(res =>{
        console.log(res)});
    }
  }

}
