import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CottageService} from '../../../service/cottage.service';
import {AdventureDTO} from '../../../model/adventure-dto';

@Component({
  selector: 'app-one-adventure',
  templateUrl: './one-adventure.component.html',
  styleUrls: ['./one-adventure.component.css']
})
export class OneAdventureComponent implements OnInit {

  adventure: AdventureDTO = ({} as any);

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
    this.httpClient.get<any>(this.config.adventure_url + '/get_adventure/' + this.router.snapshot.params.id).subscribe(
      response => {
        this.adventure = response;
      });
  }
}
