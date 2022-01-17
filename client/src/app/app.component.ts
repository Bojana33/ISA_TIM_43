import { Component } from '@angular/core';
import {UserService} from "./service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  constructor(private userService: UserService) {}

  ngOnInit() {
    console.log(this.userService.initUser());
  }
}
