import { Component, OnInit } from '@angular/core';
import {ReservationDTO} from "../../model/reservation-dto.model";
import {EntityDTO} from "../../model/entity-dto";
import {ClientService} from "../../service/client.service";
import { EntityService } from 'src/app/service/entity.service';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {

  displayedColumns: string[] = ['Entity name', 'Description', 'Price per day', 'Max num of guests', 'Unsubscribe'];
  dataSource: Array<EntityDTO> = [];

  constructor(private clientService: ClientService, private entityService: EntityService, private snackBar: MatSnackBar,) { }

  ngOnInit(): void {
    this.clientService.getAllSubscriptions().subscribe(res => {
      this.dataSource = res;
    });
  }

  unsubscribe(element: any) {
      this.entityService.unsubscribe(element.id).subscribe();
      this.snackBar.open('You are not subscribed anymore to this entity.', 'cancel');
      this.ngOnInit();
  }
}
