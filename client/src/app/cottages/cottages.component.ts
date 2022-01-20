import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageService} from '../service/cottage.service';
import {CottageDTO} from '../model/cottage-dto.model';
import {ActivatedRoute} from '@angular/router';
import {ConfigService} from '../service/config.service';
import {ApiService} from '../service/api.service';
import {UserService} from '../service/user.service';
import {User} from '../model/user';

@Component({
  selector: 'app-cottages',
  templateUrl: './cottages.component.html',
  styleUrls: ['./cottages.component.css']
})
export class CottagesComponent implements OnInit {
  cottages: CottageDTO[] = [];
  allCottages: CottageDTO[] = [];
  user!: any;
  constructor(private cottageService: CottageService,
              private httpClient: HttpClient,
              private config: ConfigService,
              private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe(res => {
      this.user = this.userService.currentUser;
      this.cottageService.getAllForOwner(Number(this.user.id)).subscribe(results => {
        this.cottages = results;
        this.allCottages = this.cottages;
      });
    });
  }
  filterCottages(cottageName: string){
    this.cottages = this.allCottages.filter((val) => val.cottageName.toUpperCase().includes(cottageName)
      || val.cottageName.toLowerCase().includes(cottageName));
  }

}
