import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {Adventure} from '../../model/adventure';
import {Cottage} from '../../model/cottage';

@Component({
  selector: 'app-cottages',
  templateUrl: './cottages-user.component.html',
  styleUrls: ['./cottages-user.component.css']
})
export class CottagesUserComponent implements OnInit {

  allCottages: Cottage[] = [];
  cottages: Cottage[] = [];
  cottage: any;
  searchTerm: any;
  searchFilter: any;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) { }

  ngOnInit(): void {
    this.getCottages();
  }

  // tslint:disable-next-line:typedef
  getCottages(){
    this.httpClient.get<any>(this.config.cottage_url + '/get_all').subscribe(
      (data) => {
        this.cottages = data;
        this.allCottages = this.cottages;
      });
  }

  search(search: any, value: any): void {
    console.log(value);
    if (search === 'name') {
      this.cottages = this.allCottages.filter((val) => val.name.toUpperCase().includes(value) || val.name.toLowerCase().includes(value));
    }
    else if (search === 'pricePerDay') {
      this.cottages = this.allCottages.filter((val) => Number(val.pricePerDay) === Number(value));
    }
    else if (search === 'averageGrade') {
      this.cottages = this.allCottages.filter((val) => Number(val.averageGrade) === Number(value));
    }
    //else if (search === 'location') {
      //this.cottages = this.allCottages.filter((val) => val.address.includes(value) || val.name.toLowerCase().includes(value));
    //}
  }
}
