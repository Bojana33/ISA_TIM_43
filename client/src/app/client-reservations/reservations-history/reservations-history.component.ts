import { Component, OnInit } from '@angular/core';
import {ReservationDTO} from "../../model/reservation-dto.model";
import {ClientService} from "../../service/client.service";
import {DatePipe} from "@angular/common";
import {ReservationService} from "../../service/reservation.service";
import { EntityService } from 'src/app/service/entity.service';

@Component({
  selector: 'app-reservations-history',
  templateUrl: './reservations-history.component.html',
  styleUrls: ['./reservations-history.component.css']
})
export class ReservationsHistoryComponent implements OnInit {

  displayedColumns: string[] = ['Reservation start date', 'Reservation end date',
    'Price', 'Number of guests', 'Discount', 'Entity', 'Description'];
  dataSource: Array<ReservationDTO> = [];
  canceledEnabled!: boolean;
  dateAdded: Date = new Date();
  selected: boolean = false;
  sortedData: Array<ReservationDTO> = [];

  constructor(private clientService: ClientService, private reservationService: ReservationService, private entityService: EntityService) { }

  ngOnInit(): void {
    this.clientService.getReservationsHistory('/all').subscribe(res => {
      this.dataSource = res;
      for(let oneReservation of res) {
        this.entityService.getEntity(oneReservation.entityId).subscribe(res => {
          console.log(res);
          oneReservation.entityName = res.name;
          oneReservation.description = res.description;
          oneReservation.pricePerDay = res.pricePerDay;
          this.dataSource.push(oneReservation);
        });
      }
    });
  }

  convertDateToString(date: Date) : any{
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
    return formatedReservationStartDate;
  }

  select(criterion:any){
    this.selected = true;
    if (criterion === 'adventure')
      this.clientService.getReservationsHistory('/adventure').subscribe(res => {
        this.dataSource = res;
      });
    else if (criterion === 'boat')
      this.clientService.getReservationsHistory('/boat').subscribe(res => {
        this.dataSource = res;
      });
    else if (criterion === 'cottage')
      this.clientService.getReservationsHistory('/cottage').subscribe(res => {
        this.dataSource = res;
      });
  }

  sort(criterion: any) {
    if(criterion === 'startDate') {
        this.reservationService.getSorted(this.dataSource, 'startDate').subscribe(res =>{
          this.dataSource = res.body;
        });
    }
    else if(criterion === 'endDate') {
      this.reservationService.getSorted(this.dataSource, 'endDate').subscribe(res =>{
        this.dataSource = res.body;
      });
    }
    else if(criterion === 'price') {
      this.reservationService.getSorted(this.dataSource, 'price').subscribe(res =>{
        this.dataSource = res.body;
      });
    }
    else if(criterion === 'duration'){
      this.reservationService.getSorted(this.dataSource, 'duration').subscribe(res =>{
        this.dataSource = res.body;
      });
    }
    }
}
