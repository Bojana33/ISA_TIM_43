import { Component, OnInit } from '@angular/core';
import {CottageDTO} from "../../../model/cottage-dto.model";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../../../service/config.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CottageService} from '../../../service/cottage.service';

@Component({
  selector: 'app-one-cottage',
  templateUrl: './one-cottage.component.html',
  styleUrls: ['./one-cottage.component.css']
})
export class OneCottageComponent implements OnInit {

  cottage: CottageDTO = ({} as any);

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
    this.httpClient.get<any>(this.config.cottages_url + '/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.cottage = response;
      });
  }
}
