import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {Adventure} from '../../model/adventure';
import {Cottage} from '../../model/cottage';
import { CottageDTO } from 'src/app/model/cottage-dto.model';

@Component({
  selector: 'app-cottages-client',
  templateUrl: './cottages-user.component.html',
  styleUrls: ['./cottages-user.component.css']
})
export class CottagesUserComponent implements OnInit {

  allCottages: CottageDTO[] = [];
  cottages: CottageDTO[] = [];
  cottage: any;
  searchTerm: any;
  searchFilter: any;
  showAll!: boolean;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) {
    this.showAll = true;
  }

  ngOnInit(): void {
    if(this.showAll)
      this.getCottages();
  }

  // tslint:disable-next-line:typedef
  getCottages(){
    this.httpClient.get<any>(this.config.cottage_url + '/get_all').subscribe(
      (data) => {
        this.cottages = data;
      });
  }

  showCottages(cottagesToDisplay: CottageDTO[]){
    this.showAll = false;
    this.cottages = cottagesToDisplay;
  }

  search(search: any, value: any): void {
    console.log(value);
    if (search === 'name') {
      this.cottages = this.allCottages.filter((val) => val.cottageName.toUpperCase().includes(value) || val.cottageName.toLowerCase().includes(value));
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
