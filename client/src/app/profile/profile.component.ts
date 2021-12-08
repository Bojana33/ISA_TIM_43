import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../service/config.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../service/user.service';
import {FormControl, FormGroup} from '@angular/forms';
import {Address} from '../model/address';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userProfile: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe((response) =>
     this.userProfile = response);
  }
}
