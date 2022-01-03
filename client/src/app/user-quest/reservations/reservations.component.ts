import { Component, OnInit } from '@angular/core';
import {Boat} from '../../model/boat';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  allReservations: Boat[] = [];
  displayedColumns: string[] = ['guests', 'price', 'notes', 'start', 'end', 'status'];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService
  ) { }

  ngOnInit(): void {
    this.getReservations();
  }

  // tslint:disable-next-line:typedef
  getReservations(){
    this.httpClient.get<any>(this.config.client_url + '/get_client_reservations').subscribe(
      (data) => {
        console.log(data);
        this.allReservations = data;
      });
  }

}
