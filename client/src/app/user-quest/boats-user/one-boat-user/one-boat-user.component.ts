import { Component, OnInit } from '@angular/core';
import {AdventureDTO} from "../../../model/adventure-dto";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../../../service/config.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CottageService} from "../../../service/cottage.service";
import {FormBuilder} from "@angular/forms";
import {BoatDTO} from "../../../model/boat-dto";

@Component({
  selector: 'app-one-boat-user',
  templateUrl: './one-boat-user.component.html',
  styleUrls: ['./one-boat-user.component.css']
})
export class OneBoatUserComponent implements OnInit {

  boat: BoatDTO = ({} as any);

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private snackbar: MatSnackBar,
    private cottageService: CottageService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    console.log(this.router.snapshot.params.id);
    this.httpClient.get<any>(this.config.boat_url + '/get_boat/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.boat = response;
      });
  }
}