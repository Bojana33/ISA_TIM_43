import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {Adventure} from '../../model/adventure';
import {Cottage} from '../../model/cottage';
import { CottageDTO } from 'src/app/model/cottage-dto.model';
import { UserService } from 'src/app/service/user.service';

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
  sortedCottages: CottageDTO[] = [];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private userService: UserService
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
        this.allCottages = data;
      });
  }

  showCottages(cottagesToDisplay: CottageDTO[]){
    this.showAll = false;
    this.cottages = cottagesToDisplay;
  }

  search(search: any, value: any): void {
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

  sort(criterion: any) {
    if(criterion === 'name')
      this.allCottages.sort((a,b) => a.cottageName > b.cottageName ? 1 : -1);
    else if(criterion === 'price')
      this.allCottages.sort((a, b) => a.pricePerDay > b.pricePerDay ? 1 : -1);
    else if(criterion === 'location')
      this.allCottages.sort(function(a, b) {
        if (a.address.country !== b.address.country) {
          // Price is only important when cities are the same
          return a.address.country > b.address.country ? 1 : -1;
        }
        if (a.address.city !== b.address.city) {
          return a.address.city > b.address.city ? 1 : -1;
        }
        else if (a.address.street !== b.address.street){
          return a.address.street > b.address.street ? 1 : -1;
        }
        else
          return a.address.houseNumber > b.address.houseNumber ? 1 : -1;
      });
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
  }
}
