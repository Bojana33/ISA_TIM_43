import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CottageDTO} from '../model/cottage-dto.model';
import {ConfigService} from '../service/config.service';


@Component({
  selector: 'app-cottage',
  templateUrl: './cottage.component.html',
  styleUrls: ['./cottage.component.css']
})
export class CottageComponent implements OnInit {
  cottage: CottageDTO | undefined;

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
  ) {
  }

  ngOnInit(): void {
    this.httpClient.get<any>(this.config.cottages_url + '').subscribe(
      response => {
        this.cottage = response;
      });
  }

}
