import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {any} from 'codelyzer/util/function';

@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.css']
})
export class ProfileUpdateComponent implements OnInit {

  firstName: any;
  surname: any;
  phoneNumber: any;
  userProfile = new FormGroup({
    firstName: new FormControl(''),
    surname: new FormControl(''),
    phoneNumber: new FormControl('')
  })

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private router2: Router,
    private userService: UserService) {
  }

  ngOnInit(): void {
    console.log(this.router.snapshot.params.id);
    this.userService.getMyInfo().subscribe(res => console.log(res));
    this.userService.getMyInfo().subscribe(res => this.userProfile = new FormGroup({
      firstName: new FormControl(res['firstName']),
      surname: new FormControl(res['surname']),
      phoneNumber: new FormControl(res['phoneNumber'])
    }));
  }

  onSubmit(values: any){
    console.log(values);
    this.userService.update(values);
    this.router2.navigate(['/profile']);
  }
}
