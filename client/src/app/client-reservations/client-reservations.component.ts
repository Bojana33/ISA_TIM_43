import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ReservationDTO} from "../model/reservation-dto.model";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CottageService} from "../service/cottage.service";
import {FreeEntityDTO} from "../model/free-entity-dto";
import {CottagesUserComponent} from "../user-quest/cottages-user/cottages-user.component";
import {Cottage} from "../model/cottage";
import {MatSnackBar} from "@angular/material/snack-bar";
import { CottageDTO } from '../model/cottage-dto.model';
import {OneCottageComponent} from "../user-quest/cottages-user/one-cottage/one-cottage.component";
import {AdditionalServicesDTO} from "../model/AdditionalServicesModel/additional-services-dto.model";
import {EntityService} from "../service/entity.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../service/user.service";
import { MatDialog } from '@angular/material/dialog';
import {ReservationDialogComponent} from "./reservation-dialog/reservation-dialog.component";
import { BoatService } from '../service/boat.service';
import {AdventureService} from "../service/adventure.service";

export interface DialogData {
  id: any;
  entityId: any;
  numberOfGuests: any;
  reservationStartDate: any;
  reservationEndDate: any;
  additionalServices: any;
  clientId: any;
}

@Component({
  selector: 'app-client-reservations',
  templateUrl: './client-reservations.component.html',
  styleUrls: ['./client-reservations.component.css']
})
export class ClientReservationsComponent implements OnInit {

  oneEntityId: any;
  userId: any;
  form!: FormGroup;
  request!: FreeEntityDTO;
  entity: CottageDTO[] = [];
  sort!: boolean;
  checkboxFlag: Array<AdditionalServicesDTO> = [];
  user!: any;
  entityValue: any;
  public sortMethod: string[] = [];
  public value: string = '';
  constructor(private formBuilder: FormBuilder,
              private cottageService: CottageService,
              private boatService: BoatService,
              private snackBar: MatSnackBar,
              private entityService: EntityService,
              private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private adventureService: AdventureService,
              public dialog: MatDialog) { this.sort = false;}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      entityType: '',
      numberOfGuests: '',
      reservationStartDate: '',
      reservationEndDate: '',
      grade: '',
      country: '',
      city: ''
    });
  }

  chooseEntity(value: any){
    if(value === 1)
      this.adventureService.getFreeAdventures(this.request).subscribe(
        res => {
          this.entity = res.body;
          if (this.entity.length === 0)
            this.snackBar.open('There are no free adventures with this specification. Try with another period.', 'cancel');
        }
      );
    else if(value === 2)
      this.boatService.getFreeBoats(this.request).subscribe(
        res => {
          this.entity = res.body;
          if (this.entity.length === 0)
            this.snackBar.open('There are no free boats with this specification. Try with another period.', 'cancel');
        }
      );
    else if(value === 3) {
      this.cottageService.getFreeCottages(this.request).subscribe(
        res => {
          this.entity = res.body;
          if (this.entity.length === 0)
            this.snackBar.open('There are no free cottages with this specification. Try with another period.', 'cancel');
        }
      );
    }
  }

  chooseEntityValue(value:number){
    this.entityValue = value;
  }

  openReservationDialog(): void {
    const dialogRef = this.dialog.open(ReservationDialogComponent, {
      width: '250px',
      data: {entityId: this.oneEntityId, numberOfGuests: this.request.numberOfGuests, reservationStartDate: this.request.startDate, reservationEndDate: this.request.endDate, additionalServices: this.checkboxFlag, clientId: this.userId.id},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  submit(): void {
    this.request = new FreeEntityDTO();
    this.request.startDate = this.form.value.reservationStartDate.toISOString();
    this.request.endDate = this.form.value.reservationEndDate.toISOString();
    this.request.type = 'cottage';
    this.request.numberOfGuests = this.form.value.numberOfGuests;
    this.request.grade = this.form.value.grade;
    this.request.country = this.form.value.country;
    this.request.city = this.form.value.city;
    this.sort = true;
    console.log(this.request);
    this.chooseEntity(this.entityValue);
  }

  ableSort(): void {
      this.sortMethod = ['Price ascending', 'Price descending', 'Average grade ascending', 'Average grade descending'];
  }

  onItemChange(item:any){
    this.value = item;
    console.log(item);
    if(item === 'Price ascending')
      this.openDialog('price', true);
    else if (item === 'Price descending')
      this.openDialog('price', false);
    else if (item === 'Average grade ascending')
      this.openDialog('grade', true);
    else if (item === 'Average grade descending')
      this.openDialog('grade', false);
    else
      this.snackBar.open('Entities can be sorted by price and average grade');
  }

  openDialog(criterion: string, asc: boolean){
    this.cottageService.getSorted(this.entity, criterion, asc).subscribe(
      res => {
        this.entity = res.body;
      }
    )
  }

  reserve(itemId: any) {
    this.oneEntityId = itemId;
    //this.userId = this.user.id;
    this.userService.getMyInfo().subscribe((response) =>
      this.userId = response);
    this.openReservationDialog();
    //this.entityService.reserve(this.checkboxFlag, itemId, this.user.id, this.request).subscribe(
      //res => {console.log('res ispisan', res);}
    //)
  }

  addAdditionalService(serv: AdditionalServicesDTO){
    this.checkboxFlag.push(serv);
  }
}
