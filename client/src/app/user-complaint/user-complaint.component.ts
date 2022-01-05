import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-complaint',
  templateUrl: './user-complaint.component.html',
  styleUrls: ['./user-complaint.component.css']
})
export class UserComplaintComponent implements OnInit {

  searchTerm: any;
  searchFilter: any;

  constructor() { }

  ngOnInit(): void {
  }

}
