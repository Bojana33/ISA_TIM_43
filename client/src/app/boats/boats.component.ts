import { Component, OnInit } from '@angular/core';
import {BoatService} from '../service/boatService/boat.service';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {BoatDTO} from '../model/boat-dto';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-boats',
  templateUrl: './boats.component.html',
  styleUrls: ['./boats.component.css']
})
export class BoatsComponent implements OnInit {
  allBoats: BoatDTO[] = [];
  boats: BoatDTO[] = [];
  user!: any;
  constructor(private boatService: BoatService,
              private httpClient: HttpClient,
              private config: ConfigService,
              private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe(res => {
      this.user = this.userService.currentUser;
      this.boatService.getAllForOwner(Number(this.user.id)).subscribe(results => {
        this.boats = results;
        this.allBoats = this.boats;
      });
    });
  }
  filterBoats(boatName: string): void{
    this.boats = this.allBoats.filter((boat) => boat.name.toUpperCase().includes(boatName)
      || boat.name.toLowerCase().includes(boatName));
  }

}
